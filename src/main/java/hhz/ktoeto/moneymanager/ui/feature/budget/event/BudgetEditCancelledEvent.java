package hhz.ktoeto.moneymanager.ui.feature.budget.event;

import org.springframework.context.ApplicationEvent;

public class BudgetEditCancelledEvent extends ApplicationEvent {

    public BudgetEditCancelledEvent(Object source) {
        super(source);
    }
}
