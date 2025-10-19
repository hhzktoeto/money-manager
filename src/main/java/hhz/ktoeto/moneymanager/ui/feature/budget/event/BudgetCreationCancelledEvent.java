package hhz.ktoeto.moneymanager.ui.feature.budget.event;

import org.springframework.context.ApplicationEvent;

public class BudgetCreationCancelledEvent extends ApplicationEvent {

    public BudgetCreationCancelledEvent(Object source) {
        super(source);
    }
}
