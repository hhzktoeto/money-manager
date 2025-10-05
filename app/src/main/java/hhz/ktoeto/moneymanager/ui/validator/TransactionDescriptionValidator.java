package hhz.ktoeto.moneymanager.ui.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class TransactionDescriptionValidator implements Validator<String> {

    private final int maxLength = 1000;
    private final String errorMessage = "Максимальная длина описания не может превышать 1000 символов";

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null || value.length() <= maxLength) {
            return ValidationResult.ok();
        }

        return ValidationResult.error(errorMessage);
    }
}
