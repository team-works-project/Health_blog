package com.website.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central place that decides which endpoints require login, which require the
 * ADMIN role, and which are open to everyone. Rules are checked top to bottom -
 * the first matching rule wins, so order matters here.
 */
@Configuration
public class SecurityConfiguration {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        // We use JWTs instead of cookies/sessions, so no server-side session is kept.
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Anyone can sign up, sign in, or refresh their token.
            .requestMatchers("/api/auth/signup", "/api/auth/signin", "/api/auth/refresh", "/h2-console/**")
            .permitAll()
            // Anyone can read posts and categories without logging in.
            .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
            // Anyone can browse tags without logging in.
            .requestMatchers(HttpMethod.GET, "/api/tags/**").permitAll()
            // Posting or deleting a comment requires being logged in (any role).
            .requestMatchers(HttpMethod.POST, "/api/posts/*/comments").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/comments/**").authenticated()
            // Everything else under these paths (create/update/delete) is admin-only.
            .requestMatchers("/api/categories/**").hasRole("ADMIN")
            .requestMatchers("/api/posts/**").hasRole("ADMIN")
            .requestMatchers("/api/admin/tags/**").hasRole("ADMIN")
            // Any endpoint not covered above still requires login by default.
            .anyRequest().authenticated())
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
