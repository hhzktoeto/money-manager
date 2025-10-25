package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class BudgetDeletedEvent extends ApplicationEvent {

    public BudgetDeletedEvent(Object source) {
        super(source);
    }
}
