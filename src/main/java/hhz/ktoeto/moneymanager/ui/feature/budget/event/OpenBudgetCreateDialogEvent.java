package hhz.ktoeto.moneymanager.ui.feature.budget.event;

import org.springframework.context.ApplicationEvent;

public class OpenBudgetCreateDialogEvent extends ApplicationEvent {

    public OpenBudgetCreateDialogEvent(Object source) {
        super(source);
    }
}
