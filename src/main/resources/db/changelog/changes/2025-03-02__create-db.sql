--liquibase formatted sql

--changeset AI:create-table-users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password TEXT NOT NULL, -- в зашифрованном виде (BCrypt)
    roles TEXT[],           -- массив ролей, если нужно
    created_at TIMESTAMP DEFAULT now()
);