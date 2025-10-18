CREATE TABLE budgets
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name        VARCHAR(64)    NOT NULL,
    type        VARCHAR(7)     NOT NULL
        CHECK (type IN ('INCOME', 'EXPENSE')),
    period_type VARCHAR(9)     NOT NULL
        CHECK (period_type in ('MONTHLY', 'YEARLY', 'UNLIMITED', 'CUSTOM')),
    goal_amount DECIMAL(12, 2) NOT NULL,
    start_date  DATE,
    end_date    DATE,
    user_id     BIGINT         NOT NULL,
    created_at  timestamptz DEFAULT current_timestamp,
    updated_at  timestamptz DEFAULT current_timestamp,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE budget_categories
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    budget_id   BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    CONSTRAINT fk_budget_category_id FOREIGN KEY (budget_id) REFERENCES budgets (id),
    CONSTRAINT fk_category_budget_id FOREIGN KEY (category_id) REFERENCES categories (id)
);