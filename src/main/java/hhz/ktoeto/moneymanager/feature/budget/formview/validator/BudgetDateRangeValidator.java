package hhz.ktoeto.moneymanager.feature.budget.formview.validator;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.RequiredArgsConstructor;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;

@RequiredArgsConstructor
public class BudgetDateRangeValidator implements Validator<DateRangeModel<SimpleDateRange>> {

    private final Checkbox renewableCheckbox;

    private static final String END_BEFORE_START_ERROR_MESSAGE = "Конец периода не может быть раньше, чем его начало";
    private static final String NO_RANGE_ERROR_MESSAGE = "Не выбран период действия бюджета";
    private static final String NO_START_ERROR_MESSAGE = "Не выбрана дата начала действия бюджета";
    private static final String NO_END_ERROR_MESSAGE = "Не выбрана дата окончания действия бюджета";

    @Override
    public ValidationResult apply(DateRangeModel<SimpleDateRange> dateRange, ValueContext valueContext) {
        boolean isRenewable = renewableCheckbox.getValue();
        if (isRenewable) {
            return ValidationResult.ok();
        }
        if (dateRange.getStart() == null && dateRange.getEnd() == null) {
            return ValidationResult.error(NO_RANGE_ERROR_MESSAGE);
        }
        if (dateRange.getStart() == null) {
            return ValidationResult.error(NO_START_ERROR_MESSAGE);
        }
        if (dateRange.getEnd() == null) {
            return ValidationResult.error(NO_END_ERROR_MESSAGE);
        }
        if (dateRange.getEnd().isBefore(dateRange.getStart())) {
            return ValidationResult.error(END_BEFORE_START_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
