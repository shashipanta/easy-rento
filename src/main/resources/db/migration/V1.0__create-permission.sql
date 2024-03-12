-- CREATE DEFAULT PERMISSION TABLE IF NOT EXIST

CREATE SEQUENCE IF NOT EXISTS permissions_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE permissions
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
    ADD CONSTRAINT uk_permissions_name UNIQUE ();