ALTER TABLE budget_goals
    ADD user_id BIGINT,
    ADD CONSTRAINT fk_budget_goals_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;