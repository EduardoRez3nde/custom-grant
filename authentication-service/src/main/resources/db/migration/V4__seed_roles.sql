CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO tb_roles (id, authority) VALUES
  (gen_random_uuid(), 'ROLE_USER'),
  (gen_random_uuid(), 'ROLE_ADMIN');
