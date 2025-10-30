package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BudgetEditRequested extends ApplicationEvent {

    private final transient Budget budget;

    public BudgetEditRequested(Object source, Budget budget) {
        super(source);
        this.budget = budget;
    }
}
