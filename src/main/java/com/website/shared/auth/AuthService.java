package com.website.shared.auth;

import com.website.shared.metadata.Metadata;

public interface AuthService {
  TokenResponse signup(SignupRequest request);

  TokenResponse signin(SigninRequest request);

  TokenResponse refresh(String refreshTokenValue);

  void logout(String refreshTokenValue);

  UserResponse me(Metadata metadata);
}
