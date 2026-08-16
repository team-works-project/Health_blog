UPDATE users SET authority = 'ADMIN' WHERE authority = 'ROLE_ADMIN';
UPDATE users SET authority = 'USER' WHERE authority = 'ROLE_USER';