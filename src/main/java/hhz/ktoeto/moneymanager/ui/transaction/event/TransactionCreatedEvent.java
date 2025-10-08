package hhz.ktoeto.moneymanager.ui.transaction.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreatedEvent extends ApplicationEvent {

    public TransactionCreatedEvent(Object source) {
        super(source);
    }
}
