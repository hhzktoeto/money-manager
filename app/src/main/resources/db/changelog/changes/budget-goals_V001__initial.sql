CREATE TABLE IF NOT EXISTS budget_goals
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    type        VARCHAR(7)     NOT NULL
        CHECK (type IN ('INCOME', 'EXPENSE')),
    category_id BIGINT         NOT NULL,
    goal_amount DECIMAL(12, 2) NOT NULL,
    created_at  timestamptz DEFAULT current_timestamp,
    updated_at  timestamptz DEFAULT current_timestamp,

    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
)