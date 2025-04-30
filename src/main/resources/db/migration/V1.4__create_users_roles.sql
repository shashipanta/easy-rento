CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id         BIGINT NOT NULL
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_roles_id FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_users_roles_permissions_id FOREIGN KEY (permission_id) REFERENCES permissions (id);

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2);