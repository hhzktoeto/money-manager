package hhz.ktoeto.moneymanager.ui.login.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.util.regex.Pattern;

public class UsernameValidator implements Validator<String> {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 64;
    private static final String VALID_PATTERN = "^[a-zA-Z0-9]+$";

    private static final String MIN_LENGTH_ERROR_MESSAGE = "Логин не может быть короче 3 символов";
    private static final String MAX_LENGTH_ERROR_MESSAGE = "Логин не может быть длиннее 64 символов";
    private static final String INVALID_PATTERN_ERROR_MESSAGE = "Логин содержит недопустимые символы";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value.length() < MIN_LENGTH) {
            return ValidationResult.error(MIN_LENGTH_ERROR_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            return ValidationResult.error(MAX_LENGTH_ERROR_MESSAGE);
        }
        if (!Pattern.matches(VALID_PATTERN, value)) {
            return ValidationResult.error(INVALID_PATTERN_ERROR_MESSAGE);
        }
        return ValidationResult.ok();
    }
}
