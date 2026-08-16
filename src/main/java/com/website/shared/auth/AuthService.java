package com.website.shared.auth;

import com.website.shared.metadata.Metadata;

public interface AuthService {
    SignupResponse signup(SignupRequest request);

    TokenResponse verifyEmail(VerifyEmailRequest request);

    void resendVerificationCode(ResendCodeRequest request);

    TokenResponse signin(SigninRequest request);

    TokenResponse refresh(String refreshTokenValue);

    void logout(String refreshTokenValue);

    UserResponse me(Metadata metadata);
}