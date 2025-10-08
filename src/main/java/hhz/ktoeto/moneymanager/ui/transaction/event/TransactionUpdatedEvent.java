package hhz.ktoeto.moneymanager.ui.transaction.event;

import org.springframework.context.ApplicationEvent;

public class TransactionUpdatedEvent extends ApplicationEvent {

    public TransactionUpdatedEvent(Object source) {
        super(source);
    }
}
