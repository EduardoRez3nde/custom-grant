DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS tb_users;
DROP TABLE IF EXISTS tb_roles;

CREATE TABLE tb_roles (
    id UUID PRIMARY KEY,
    authority VARCHAR(255) NOT NULL
);

CREATE TABLE tb_users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id)
        REFERENCES tb_roles(id)
        ON DELETE RESTRICT
);

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO tb_roles (id, authority) VALUES
  (gen_random_uuid(), 'ROLE_TEACHER'),
  (gen_random_uuid(), 'ROLE_STUDENT');
