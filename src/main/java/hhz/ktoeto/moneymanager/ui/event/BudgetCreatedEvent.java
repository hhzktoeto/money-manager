package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BudgetCreatedEvent extends ApplicationEvent {

    private final transient Budget budget;

    public BudgetCreatedEvent(Object source, Budget budget) {
        super(source);
        this.budget = budget;
    }
}
