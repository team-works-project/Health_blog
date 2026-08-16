package com.website.shared.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {
    Optional<EmailVerificationCode> findFirstByUserIdAndCodeAndUsedFalseOrderByCreatedAtDesc(
            String userId, String code);
}