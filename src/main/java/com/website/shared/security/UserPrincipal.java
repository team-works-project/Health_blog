package com.website.shared.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Wraps our UserAccount entity so Spring Security can use it during authentication.
 * Spring Security needs an object that implements UserDetails - this class adapts
 * our own UserAccount to that interface instead of forcing UserAccount to implement it.
 */
public class UserPrincipal implements UserDetails {
  private final UserAccount user;

  public UserPrincipal(UserAccount user) {
    this.user = user;
  }

  public String getId() {
    return user.getId();
  }

  public UserAccount getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getAuthority().name()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isEnabled() {
    return user.isEnabled();
  }
}
