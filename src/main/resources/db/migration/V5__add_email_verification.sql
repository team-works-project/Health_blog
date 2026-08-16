ALTER TABLE users ADD COLUMN email_verified BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE users SET email_verified = TRUE;

CREATE TABLE email_verification_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    code VARCHAR(6) NOT NULL,
    expires_at DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evc_user FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE INDEX idx_evc_user_id ON email_verification_codes(user_id);