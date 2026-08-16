package com.website.shared.auth;

import com.website.shared.mail.EmailService;
import com.website.shared.metadata.Metadata;
import com.website.shared.metadata.MetadataHandler;
import com.website.shared.security.AuthorityEnum;
import com.website.shared.security.EmailVerificationCode;
import com.website.shared.security.EmailVerificationCodeRepository;
import com.website.shared.security.JwtService;
import com.website.shared.security.RefreshToken;
import com.website.shared.security.RefreshTokenRepository;
import com.website.shared.security.UserAccount;
import com.website.shared.security.UserRepository;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailVerificationCodeRepository verificationCodeRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final long refreshTokenDays;
    private final long codeExpiryMinutes;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthServiceImpl(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            EmailVerificationCodeRepository verificationCodeRepository,
            AuthMapper authMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            EmailService emailService,
            @Value("${security.jwt.refresh-token-days}") long refreshTokenDays,
            @Value("${verification.code-expiry-minutes:10}") long codeExpiryMinutes) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.refreshTokenDays = refreshTokenDays;
        this.codeExpiryMinutes = codeExpiryMinutes;
    }

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        UserAccount user = authMapper.from(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthority(AuthorityEnum.USER);
        user.setEmailVerified(false);
        userRepository.save(user);

        issueAndSendVerificationCode(user);

        return new SignupResponse(
                user.getEmail(), "Account created. Check your email for the verification code.");
    }

    @Override
    @Transactional
    public TokenResponse verifyEmail(VerifyEmailRequest request) {
        UserAccount user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or code"));

        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified");
        }

        EmailVerificationCode verification = verificationCodeRepository
                .findFirstByUserIdAndCodeAndUsedFalseOrderByCreatedAtDesc(user.getId(), request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired code"));

        if (verification.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Code has expired, please request a new one");
        }

        verification.setUsed(true);
        verificationCodeRepository.save(verification);

        user.setEmailVerified(true);
        userRepository.save(user);

        return issueTokens(user);
    }

    @Override
    @Transactional
    public void resendVerificationCode(ResendCodeRequest request) {
        UserAccount user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No account found with that email"));

        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified");
        }

        issueAndSendVerificationCode(user);
    }

    @Override
    @Transactional
    public TokenResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserAccount user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException("Please verify your email before signing in");
        }

        return issueTokens(user);
    }

    @Override
    @Transactional
    public TokenResponse refresh(String refreshTokenValue) {
        RefreshToken current = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token is invalid"));
        if (current.isRevoked() || current.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token is expired or revoked");
        }
        current.setRevoked(true);
        refreshTokenRepository.save(current);
        return issueTokens(current.getUser());
    }

    @Override
    @Transactional
    public void logout(String refreshTokenValue) {
        refreshTokenRepository.findByToken(refreshTokenValue).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    @Override
    @MetadataHandler
    public UserResponse me(Metadata metadata) {
        UserAccount user = userRepository.findById(metadata.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return authMapper.toResponse(user);
    }

    private void issueAndSendVerificationCode(UserAccount user) {
        String code = generateCode();
        EmailVerificationCode verification = new EmailVerificationCode();
        verification.setUserId(user.getId());
        verification.setCode(code);
        verification.setExpiresAt(Instant.now().plus(codeExpiryMinutes, ChronoUnit.MINUTES));
        verificationCodeRepository.save(verification);
        emailService.sendVerificationCode(user.getEmail(), user.getDisplayName(), code);
    }

    private String generateCode() {
        int number = secureRandom.nextInt(1_000_000);
        return String.format("%06d", number);
    }

    private TokenResponse issueTokens(UserAccount user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plus(refreshTokenDays, ChronoUnit.DAYS));
        refreshTokenRepository.save(refreshToken);
        return new TokenResponse(
                jwtService.createAccessToken(user),
                refreshToken.getToken(),
                refreshToken.getExpiresAt(),
                "Bearer");
    }
}