package hhz.ktoeto.moneymanager.ui.feature.budget.event;

import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BudgetUpdatedEvent extends ApplicationEvent {

    private final Budget budget;

    public BudgetUpdatedEvent(Object source, Budget budget) {
        super(source);
        this.budget = budget;
    }
}
