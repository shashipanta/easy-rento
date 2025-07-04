-- add default role

CREATE SEQUENCE IF NOT EXISTS roles_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS roles
(
    id               BIGINT       NOT NULL,
    created_by       BIGINT       NOT NULL,
    created_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by BIGINT       NOT NULL,
    last_modified_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    active           BOOLEAN      NOT NULL,
    status           VARCHAR(255) NOT NULL,
    name             VARCHAR(200) NOT NULL,
    description      VARCHAR(255),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conname = 'uk_roles_name'
        ) THEN
            ALTER TABLE roles
                ADD CONSTRAINT uk_roles_name UNIQUE (name);
        END IF;
    END;
$$;

-- default roles
INSERT INTO roles (id, active, created_by, created_on, last_modified_by, last_modified_on, status, description, name)
VALUES
    (nextval('roles_seq'), true, 1, now(), 1, now(), 'APPROVED', 'ALL PRIVILEGES', 'SUPER_ADMIN'),
    (nextval('roles_seq'), true, 1, now(), 1, now(), 'APPROVED', 'LIMITED PRIVILEGES', 'ADMIN'),
    (nextval('roles_seq'), true, 1, now(), 1, now(), 'APPROVED', 'USER WITH SOME PRIVILEGES', 'OWNER'),
    (nextval('roles_seq'), true, 1, now(), 1, now(), 'APPROVED', 'USER WITH SOMEWHAT NO PRIVILEGES', 'TENANT');