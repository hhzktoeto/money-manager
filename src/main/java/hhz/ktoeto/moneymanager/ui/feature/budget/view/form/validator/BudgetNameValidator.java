package hhz.ktoeto.moneymanager.ui.feature.budget.view.form.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class BudgetNameValidator implements Validator<String> {

    private static final int MAX_LENGTH = 64;

    private static final String MAX_LENGTH_ERROR_MESSAGE = "Название бюджета не должно быть длиннее 64 символов";
    private static final String EMPTY_ERROR_MESSAGE = "Не указано название бюджета";

    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        if (value == null || value.isBlank()) {
            return ValidationResult.error(EMPTY_ERROR_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            return ValidationResult.error(MAX_LENGTH_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
