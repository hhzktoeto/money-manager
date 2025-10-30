package hhz.ktoeto.moneymanager.feature.category.formview.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class CategoryNameValidator implements Validator<String> {

    private static final int MAX_LENGTH = 64;

    private static final String MAX_LENGTH_ERROR_MESSAGE = "Имя категории не должно быть длиннее 64 символов";
    private static final String EMPTY_ERROR_MESSAGE = "Не указано имя категории";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null || value.isBlank()) {
            return ValidationResult.error(EMPTY_ERROR_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            return ValidationResult.error(MAX_LENGTH_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
