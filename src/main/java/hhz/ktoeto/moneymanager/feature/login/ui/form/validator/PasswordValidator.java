package hhz.ktoeto.moneymanager.feature.login.ui.form.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class PasswordValidator implements Validator<String> {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 128;

    private static final String MIN_LENGTH_ERROR_MESSAGE = "Пароль не может быть короче 8 символов";
    private static final String MAX_LENGTH_ERROR_MESSAGE = "Пароль не может быть длиннее 128 символов";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value.length() < MIN_LENGTH) {
            return ValidationResult.error(MIN_LENGTH_ERROR_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            return ValidationResult.error(MAX_LENGTH_ERROR_MESSAGE);
        }
        return ValidationResult.ok();
    }
}
