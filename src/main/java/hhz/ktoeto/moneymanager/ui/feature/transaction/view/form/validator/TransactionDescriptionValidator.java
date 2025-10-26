package hhz.ktoeto.moneymanager.ui.feature.transaction.view.form.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class TransactionDescriptionValidator implements Validator<String> {

    private static final int MAX_LENGTH = 1000;

    private static final String MAX_LENGTH_ERROR_MESSAGE = "Длина описания не может превышать 1000 символов";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null) {
            return ValidationResult.ok();
        }
        if (value.length() > MAX_LENGTH) {
            return ValidationResult.error(MAX_LENGTH_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
