package com.website.shared.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * A long-lived token stored in the database, used to get a new access token
 * without logging in again. Each one can be used once - see AuthServiceImpl.refresh(),
 * which marks it revoked and issues a fresh one every time it's used.
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
  @Id
  private String id;
  private String token;
  private Instant expiresAt;
  private boolean revoked;

  @ManyToOne(optional = false)
  private UserAccount user;

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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Instant expiresAt) {
    this.expiresAt = expiresAt;
  }

  public boolean isRevoked() {
    return revoked;
  }

  public void setRevoked(boolean revoked) {
    this.revoked = revoked;
  }

  public UserAccount getUser() {
    return user;
  }

  public void setUser(UserAccount user) {
    this.user = user;
  }
}
