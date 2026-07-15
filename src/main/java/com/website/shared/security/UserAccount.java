package com.website.shared.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.UUID;

/** A registered user. The password field always stores a BCrypt hash, never plain text. */
@Entity
@Table(name = "users")
public class UserAccount {
  @Id
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private String displayName;
  private boolean enabled = true;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AuthorityEnum authority;

  @PrePersist
  void prePersist() {
    if (id == null) {
      id = UUID.randomUUID().toString();
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public AuthorityEnum getAuthority() {
    return authority;
  }

  public void setAuthority(AuthorityEnum authority) {
    this.authority = authority;
  }
}
