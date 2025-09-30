CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    login      VARCHAR(64) UNIQUE NOT NULL,
    password   VARCHAR(60)        NOT NULL,
    email      VARCHAR(128) UNIQUE,
    phone      VARCHAR(15) UNIQUE,
    name       VARCHAR(32),
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp
);

ALTER TABLE transactions
    ADD user_id BIGINT,
    ADD CONSTRAINT fk_transactions_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;

ALTER TABLE categories
    ADD user_id BIGINT,
    ADD CONSTRAINT fk_categories_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,
    ADD CONSTRAINT categories_name_user_id_unique
        UNIQUE (name, user_id);

CREATE INDEX categories_name_user_id_idx ON categories (name, user_id);
