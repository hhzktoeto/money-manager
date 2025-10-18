CREATE TABLE budgets
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name          VARCHAR(64)    NOT NULL,
    is_renewable  BOOLEAN        NOT NULL,
    type          VARCHAR(7)     NOT NULL
        CHECK (type IN ('INCOME', 'EXPENSE')),
    scope         VARCHAR(13)    NOT NULL
        CHECK (scope IN ('ALL', 'BY_CATEGORIES')),
    active_period VARCHAR(7)     NOT NULL
        CHECK (active_period in ('DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR')),
    goal_amount   DECIMAL(12, 2) NOT NULL,
    start_date    DATE           NOT NULL,
    end_date      DATE           NOT NULL,
    user_id       BIGINT         NOT NULL,
    created_at    timestamptz DEFAULT current_timestamp,
    updated_at    timestamptz DEFAULT current_timestamp,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE budget_categories
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    budget_id   BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    CONSTRAINT fk_budget_category_id FOREIGN KEY (budget_id) REFERENCES budgets (id) ON DELETE CASCADE,
    CONSTRAINT fk_category_budget_id FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    CONSTRAINT unique_budget_category UNIQUE (budget_id, category_id)
);