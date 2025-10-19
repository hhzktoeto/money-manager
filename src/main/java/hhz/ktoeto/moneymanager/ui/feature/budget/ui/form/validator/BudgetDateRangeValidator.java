package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import hhz.ktoeto.moneymanager.core.exception.InternalServerException;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;

public class BudgetDateRangeValidator implements Validator<DateRange> {

    private static final String END_BEFORE_START_ERROR_MESSAGE = "Конец периода не может быть раньше, чем его начало";
    private static final String NO_RANGE_ERROR_MESSAGE = "Не выбран период действия бюджета";
    private static final String NO_START_ERROR_MESSAGE = "Не выбрана дата начала действия бюджета";
    private static final String NO_END_ERROR_MESSAGE = "Не выбрана дата окончания действия бюджета";

    @Override
    public ValidationResult apply(DateRange dateRange, ValueContext valueContext) {
        Binder<Budget> binder = (Binder<Budget>) valueContext.getBinder().orElseThrow(
                () -> new InternalServerException("Биндер не найден при валидации категорий в бюджете")
        );
        Budget budget = binder.getBean();
        if (budget.isRenewable()) {
            return ValidationResult.ok();
        }
        if (budget.getStartDate() == null && budget.getEndDate() == null) {
            return ValidationResult.error(NO_RANGE_ERROR_MESSAGE);
        }
        if (budget.getStartDate() == null) {
            return ValidationResult.error(NO_START_ERROR_MESSAGE);
        }
        if (budget.getEndDate() == null) {
            return ValidationResult.error(NO_END_ERROR_MESSAGE);
        }
        if (budget.getEndDate().isBefore(budget.getStartDate())) {
            return ValidationResult.error(END_BEFORE_START_ERROR_MESSAGE);
        }

        return ValidationResult.ok();
    }
}
