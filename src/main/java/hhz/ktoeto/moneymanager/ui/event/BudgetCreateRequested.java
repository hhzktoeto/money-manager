package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class BudgetCreateRequested extends ApplicationEvent {

    public BudgetCreateRequested(Object source) {
        super(source);
    }
}
