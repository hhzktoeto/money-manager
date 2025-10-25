package hhz.ktoeto.moneymanager.ui.feature.budget.view.form.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class BudgetCategoriesValidator implements Validator<Set<Category>> {

    private final ToggleButtonGroup<Budget.Scope> scopeToggle;
    private static final String EMPTY_CATEGORIES_ERROR_MESSAGE = "Не выбраны категории для учёта в бюджете";

    @Override
    public ValidationResult apply(Set<Category> categories, ValueContext valueContext) {
        if (scopeToggle.getValue() == Budget.Scope.ALL) {
            return ValidationResult.ok();
        }
        if (categories == null || categories.isEmpty()) {
            return ValidationResult.error(EMPTY_CATEGORIES_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
