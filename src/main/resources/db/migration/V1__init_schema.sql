-- 1. Base Core Tables (No Dependencies)
CREATE TABLE users
(
    id           VARCHAR(36)  NOT NULL,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    enabled      BOOLEAN      NOT NULL DEFAULT TRUE,
    authority    VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE categories
(
    id          VARCHAR(36)  NOT NULL,
    name        VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_categories_name UNIQUE (name)
);

CREATE TABLE tags
(
    id         VARCHAR(36) NOT NULL,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT pk_tags PRIMARY KEY (id),
    CONSTRAINT uk_tags_name UNIQUE (name)
);

-- 2. Dependent Tables (Foreign Keys to Core Tables)
CREATE TABLE refresh_tokens
(
    id         VARCHAR(36)  NOT NULL,
    token      VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP    NOT NULL,
    revoked    BOOLEAN      NOT NULL DEFAULT FALSE,
    user_id    VARCHAR(36)  NOT NULL,
    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id),
    CONSTRAINT uk_refresh_tokens_token UNIQUE (token),
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE posts
(
    id          VARCHAR(36)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     TEXT,
    thumbnail   VARCHAR(255),
    category_id VARCHAR(36),
    author_id   VARCHAR(36),
    enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    type        VARCHAR(20)  NOT NULL DEFAULT 'BLOG',
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    CONSTRAINT pk_posts PRIMARY KEY (id),
    CONSTRAINT fk_posts_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users (id)
);

-- 3. Highly Dependent / Junction Tables (Foreign Keys to Posts)
CREATE TABLE comments
(
    id         VARCHAR(36)   NOT NULL,
    content    VARCHAR(1000) NOT NULL,
    post_id    VARCHAR(36)   NOT NULL,
    author_id  VARCHAR(36)   NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_author FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE posts_tags
(
    post_id VARCHAR(36) NOT NULL,
    tag_id  VARCHAR(36) NOT NULL,
    CONSTRAINT pk_posts_tags PRIMARY KEY (post_id, tag_id),
    CONSTRAINT fk_posts_tags_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_posts_tags_tag FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

-- 4. Initial Seed Data
INSERT INTO categories (id, name, description, enabled, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'Nutrition', 'Diet and nutrition articles', TRUE, NOW(), NOW()),
       ('22222222-2222-2222-2222-222222222222', 'Fitness', 'Exercise and fitness articles', TRUE, NOW(), NOW());