package hhz.ktoeto.moneymanager.event;

import org.springframework.context.ApplicationEvent;

public class TransactionAddedEvent extends ApplicationEvent {
    public TransactionAddedEvent(Object source) {
        super(source);
    }
}
