package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import hhz.ktoeto.moneymanager.core.exception.InternalServerException;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;

import java.util.Set;

public class BudgetCategoriesValidator implements Validator<Set<Category>> {

    private static final String EMPTY_CATEGORIES_ERROR_MESSAGE = "Не выбраны категории для учёта в бюджете";

    @Override
    public ValidationResult apply(Set<Category> categories, ValueContext valueContext) {
        Binder<Budget> binder = (Binder<Budget>) valueContext.getBinder().orElseThrow(
                () -> new InternalServerException("Биндер не найден при валидации категорий в бюджете")
        );
        Budget budget = binder.getBean();

        if (budget == null) {
            return ValidationResult.ok();
        }
        if (budget.getScope() == Budget.Scope.ALL) {
            return ValidationResult.ok();
        }
        if (categories == null || categories.isEmpty()) {
            return ValidationResult.error(EMPTY_CATEGORIES_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
