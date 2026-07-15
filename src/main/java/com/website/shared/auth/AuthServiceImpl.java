package com.website.shared.auth;

import com.website.shared.metadata.Metadata;
import com.website.shared.metadata.MetadataHandler;
import com.website.shared.security.AuthorityEnum;
import com.website.shared.security.JwtService;
import com.website.shared.security.RefreshToken;
import com.website.shared.security.RefreshTokenRepository;
import com.website.shared.security.UserAccount;
import com.website.shared.security.UserRepository;
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
  private final AuthMapper authMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final long refreshTokenDays;

  public AuthServiceImpl(
      UserRepository userRepository,
      RefreshTokenRepository refreshTokenRepository,
      AuthMapper authMapper,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      @Value("${security.jwt.refresh-token-days}") long refreshTokenDays) {
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.authMapper = authMapper;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.refreshTokenDays = refreshTokenDays;
  }

  @Override
  @Transactional
  public TokenResponse signup(SignupRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email is already registered");
    }

    UserAccount user = authMapper.from(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setAuthority(AuthorityEnum.USER);
    userRepository.save(user);
    return issueTokens(user);
  }

  @Override
  @Transactional
  public TokenResponse signin(SigninRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    UserAccount user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
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

  /**
   * Issues a fresh pair of tokens for the user: a short-lived JWT ("access token")
   * used on every request, and a long-lived, database-backed "refresh token" that
   * can be exchanged later for a new access token without asking for a password again.
   */
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
