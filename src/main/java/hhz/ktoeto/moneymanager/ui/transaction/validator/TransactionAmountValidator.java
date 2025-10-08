package hhz.ktoeto.moneymanager.ui.transaction.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.math.BigDecimal;

public class TransactionAmountValidator implements Validator<BigDecimal> {

    private final BigDecimal maxValue = new BigDecimal(99999999999L);
    private final String errorMessage = "Сумма одной транзакции не может превышать 99 999 999 999,00";

    @Override
    public ValidationResult apply(BigDecimal value, ValueContext context) {
        if (value.compareTo(maxValue) <= 0) {
            return ValidationResult.ok();
        }
        return ValidationResult.error(errorMessage);
    }
}
