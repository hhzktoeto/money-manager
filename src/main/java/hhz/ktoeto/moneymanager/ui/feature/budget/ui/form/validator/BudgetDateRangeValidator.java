package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetDateRangeValidator implements Validator<DateRange> {

    private final Checkbox renewableCheckbox;

    private static final String END_BEFORE_START_ERROR_MESSAGE = "Конец периода не может быть раньше, чем его начало";
    private static final String NO_RANGE_ERROR_MESSAGE = "Не выбран период действия бюджета";
    private static final String NO_START_ERROR_MESSAGE = "Не выбрана дата начала действия бюджета";
    private static final String NO_END_ERROR_MESSAGE = "Не выбрана дата окончания действия бюджета";

    @Override
    public ValidationResult apply(DateRange dateRange, ValueContext valueContext) {
        boolean isRenewable = renewableCheckbox.getValue();
        if (isRenewable) {
            return ValidationResult.ok();
        }
        if (dateRange.getStartDate() == null && dateRange.getEndDate() == null) {
            return ValidationResult.error(NO_RANGE_ERROR_MESSAGE);
        }
        if (dateRange.getStartDate() == null) {
            return ValidationResult.error(NO_START_ERROR_MESSAGE);
        }
        if (dateRange.getEndDate() == null) {
            return ValidationResult.error(NO_END_ERROR_MESSAGE);
        }
        if (dateRange.getEndDate().isBefore(dateRange.getStartDate())) {
            return ValidationResult.error(END_BEFORE_START_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
