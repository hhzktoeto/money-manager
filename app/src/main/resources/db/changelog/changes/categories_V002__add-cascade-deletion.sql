ALTER TABLE transactions
    DROP CONSTRAINT fk_category_id;
ALTER TABLE budget_goals
    DROP CONSTRAINT fk_category_id;

ALTER TABLE transactions
    ADD CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE CASCADE;

ALTER TABLE budget_goals
    ADD CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE CASCADE;