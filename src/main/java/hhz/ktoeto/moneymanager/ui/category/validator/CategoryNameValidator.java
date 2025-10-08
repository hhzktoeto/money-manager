package hhz.ktoeto.moneymanager.ui.category.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class CategoryNameValidator implements Validator<String> {

    private final int maxLength = 64;
    private final String errorMessage = "Длина имени не может превышать 64 символа";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value.length() <= maxLength) {
            return ValidationResult.ok();
        }
        return ValidationResult.error(errorMessage);
    }
}
