package com.website.shared.auth;

import com.website.shared.security.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private String id;
  private String email;
  private String displayName;
  private AuthorityEnum authority;
}
