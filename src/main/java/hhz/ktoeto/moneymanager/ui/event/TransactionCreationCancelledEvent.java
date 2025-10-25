package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreationCancelledEvent extends ApplicationEvent {

    public TransactionCreationCancelledEvent(Object source) {
        super(source);
    }
}
