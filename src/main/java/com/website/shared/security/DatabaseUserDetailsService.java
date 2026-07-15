package com.website.shared.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Tells Spring Security how to load a user by email (which we use as the username).
 * This is the bridge between our own UserRepository and Spring Security's login system.
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public DatabaseUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
        .map(UserPrincipal::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
