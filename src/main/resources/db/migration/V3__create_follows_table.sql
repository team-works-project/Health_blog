CREATE TABLE follows
(
    id           VARCHAR(36) NOT NULL,
    follower_id  VARCHAR(36) NOT NULL,
    following_id VARCHAR(36) NOT NULL,
    created_at   TIMESTAMP,
    CONSTRAINT pk_follows PRIMARY KEY (id),
    CONSTRAINT uk_follows_pair UNIQUE (follower_id, following_id),
    CONSTRAINT fk_follows_follower FOREIGN KEY (follower_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_follows_following FOREIGN KEY (following_id) REFERENCES users (id) ON DELETE CASCADE
);