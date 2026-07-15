package com.website.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Creates and reads JWT access tokens.
 * The token itself only stores the user's email (as the "subject") and their id -
 * everything else about the user is looked up from the database when needed.
 */
@Service
public class JwtService {
  private final SecretKey key;
  private final long accessTokenMinutes;

  public JwtService(
      @Value("${security.jwt.secret}") String secret,
      @Value("${security.jwt.access-token-minutes}") long accessTokenMinutes) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.accessTokenMinutes = accessTokenMinutes;
  }

  /** Builds a signed JWT for the given user that expires after accessTokenMinutes. */
  public String createAccessToken(UserAccount user) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(user.getEmail())
        .claim("uid", user.getId())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(accessTokenMinutes * 60)))
        .signWith(key)
        .compact();
  }

  /** Reads the user's email back out of a token. Returns null if the token is invalid. */
  public String extractEmail(String token) {
    return parseClaims(token).getSubject();
  }

  private Claims parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
