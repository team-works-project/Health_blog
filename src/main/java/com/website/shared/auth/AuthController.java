package com.website.shared.auth;

import com.website.shared.api.ApiResponse;
import com.website.shared.metadata.Metadata;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    ApiResponse<UserResponse> me() {
        return ApiResponse.ok("Current user", authService.me(new Metadata()));
    }

    @PostMapping("/signup")
    ApiResponse<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ApiResponse.ok("Account created", authService.signup(request));
    }

    @PostMapping("/verify-email")
    ApiResponse<TokenResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        return ApiResponse.ok("Email verified", authService.verifyEmail(request));
    }

    @PostMapping("/resend-code")
    ApiResponse<Void> resendCode(@Valid @RequestBody ResendCodeRequest request) {
        authService.resendVerificationCode(request);
        return ApiResponse.ok("Verification code sent", null);
    }

    @PostMapping("/signin")
    ApiResponse<TokenResponse> signin(@Valid @RequestBody SigninRequest request) {
        return ApiResponse.ok("Signed in", authService.signin(request));
    }

    @PostMapping("/refresh")
    ApiResponse<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ApiResponse.ok("Token refreshed", authService.refresh(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@Valid @RequestBody RefreshRequest request) {
        authService.logout(request.getRefreshToken());
        return ApiResponse.ok("Signed out", null);
    }
}