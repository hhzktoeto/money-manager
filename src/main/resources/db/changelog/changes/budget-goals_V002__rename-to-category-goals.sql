ALTER TABLE budget_goals RENAME TO category_goals;

ALTER TABLE category_goals
    RENAME CONSTRAINT fk_budget_goals_user_id TO fk_category_goals_user_id;

ALTER TABLE category_goals
    RENAME CONSTRAINT fk_category_id TO fk_category_goals_category_id;