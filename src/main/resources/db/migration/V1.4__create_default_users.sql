-- Insert default admin user
INSERT INTO user_accounts (id, active, user_name, email, password, status, created_on, created_by, last_modified_on,
                           last_modified_by, user_type)
VALUES (1,
        TRUE,
        'admin',
        'admin@example.com',
        '$2a$12$encryptedpasswordhere', -- BCrypt hash for 'admin123'
        'APPROVED',
        NOW(),
        1,
        NOW(),
        1,
        'ADMIN'                             -- T = Tenant, O = Owner, A = Admin
       )
ON CONFLICT (id) DO NOTHING;