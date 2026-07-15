package com.website.shared.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Runs once when the app starts up. Creates a default admin account
 * (admin@health.local / admin123) if one doesn't already exist yet,
 * so there's always a way to log in as an admin on a fresh database.
 */
@Component
public class AdminSeeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
    if (userRepository.existsByEmail("admin@health.local")) {
      return;
    }
    UserAccount admin = new UserAccount();
    admin.setEmail("admin@health.local");
    admin.setPassword(passwordEncoder.encode("admin123"));
    admin.setDisplayName("Admin");
    admin.setAuthority(AuthorityEnum.ADMIN);
    userRepository.save(admin);
  }
}
