package hhz.ktoeto.moneymanager.ui.transaction.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.math.BigDecimal;

public class TransactionAmountValidator implements Validator<BigDecimal> {

    private static final BigDecimal MAX_VALUE = new BigDecimal(99999999999L);
    private static final String MAX_VALUE_ERROR_MESSAGE = "Сумма одной транзакции не может превышать 99 999 999 999,00";

    @Override
    public ValidationResult apply(BigDecimal value, ValueContext context) {
        if (value.compareTo(MAX_VALUE) > 0) {
            return ValidationResult.error(MAX_VALUE_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
