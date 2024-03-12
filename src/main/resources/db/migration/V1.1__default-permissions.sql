
-- Insert data into permissions table with auto-generated ID and today's date
INSERT INTO permissions (id, active, created_by, created_on, last_modified_by, last_modified_on, status, alias, name)
VALUES
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'R', 'READ'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'W', 'WRITE'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'U', 'UPDATE'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'D', 'DELETE');