-- Passwords below are BCrypt hashes for the text 'password'
INSERT INTO users (id, email, password, display_name, enabled, authority)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'admin@healthblog.com', '$2a$10$R7B2j9cK.q1M2x4e6z7O1uV8Q8E.yZ7x3W1v5U6t7s8R9q0p1o2n3', 'Chief Editor', TRUE, 'ROLE_ADMIN'),
       ('f6e5d4c3-b2a1-0f9e-8d7c-6b5a4u3y2t1s', 'author@healthblog.com', '$2a$10$R7B2j9cK.q1M2x4e6z7O1uV8Q8E.yZ7x3W1v5U6t7s8R9q0p1o2n3', 'Dr. Jane Smith', TRUE, 'ROLE_USER');