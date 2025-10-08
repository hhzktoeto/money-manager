package hhz.ktoeto.moneymanager.ui.transaction.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class TransactionDescriptionValidator implements Validator<String> {

    private final int maxLength = 1000;
    private final String errorMessage = "Длина описания не может превышать 1000 символов";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null || value.length() <= maxLength) {
            return ValidationResult.ok();
        }

        return ValidationResult.error(errorMessage);
    }
}
