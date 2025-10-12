package hhz.ktoeto.moneymanager.ui.transaction.event;

import org.springframework.context.ApplicationEvent;

public class TransactionEditCancelledEvent extends ApplicationEvent {

    public TransactionEditCancelledEvent(Object source) {
        super(source);
    }
}
