package hhz.ktoeto.moneymanager.feature.budget.formview.validator;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetActivePeriodValidator implements Validator<Budget.ActivePeriod> {

    private final Checkbox renewableCheckbox;

    private static final String NO_ACTIVE_PERIOD_ERROR_MESSAGE = "Не выбран период действия бюджета";

    @Override
    public ValidationResult apply(Budget.ActivePeriod activePeriod, ValueContext valueContext) {
        boolean isRenewable = renewableCheckbox.getValue();

        if (!isRenewable) {
            return ValidationResult.ok();
        }
        if (activePeriod == null) {
            return ValidationResult.error(NO_ACTIVE_PERIOD_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
