-- CREATE DEFAULT PERMISSION TABLE IF NOT EXIST

CREATE SEQUENCE IF NOT EXISTS permissions_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS permissions
(
    id               BIGINT       NOT NULL,
    created_by       BIGINT       NOT NULL,
    created_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by BIGINT       NOT NULL,
    last_modified_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    active           BOOLEAN      NOT NULL,
    status           VARCHAR(255) NOT NULL,
    name             VARCHAR(150) NOT NULL,
    alias            VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

ALTER TABLE permissions
    ADD CONSTRAINT uk_permissions_name UNIQUE (name);

-- default permissions
INSERT INTO permissions (id, active, created_by, created_on, last_modified_by, last_modified_on, status, alias, name)
VALUES
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'R', 'READ'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'W', 'WRITE'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'U', 'UPDATE'),
    (nextval('permissions_seq'), true, 1, now(), 1, now(), 'APPROVED', 'D', 'DELETE');