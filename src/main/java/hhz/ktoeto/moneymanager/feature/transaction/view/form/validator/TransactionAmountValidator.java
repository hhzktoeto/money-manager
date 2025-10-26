package hhz.ktoeto.moneymanager.feature.transaction.view.form.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.math.BigDecimal;

public class TransactionAmountValidator implements Validator<BigDecimal> {

    private static final BigDecimal MIN_VALUE = new BigDecimal("0.01");
    private static final BigDecimal MAX_VALUE = new BigDecimal(99999999999L);

    private static final String MIN_VALUE_ERROR_MESSAGE = "Сумма транзакции не может быть меньше 0,01";
    private static final String MAX_VALUE_ERROR_MESSAGE = "Сумма транзакции не может превышать 99 999 999 999,00";

    @Override
    public ValidationResult apply(BigDecimal value, ValueContext context) {
        if (value == null || value.compareTo(MIN_VALUE) < 0) {
            return ValidationResult.error(MIN_VALUE_ERROR_MESSAGE);
        }
        if (value.compareTo(MAX_VALUE) > 0) {
            return ValidationResult.error(MAX_VALUE_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
