package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class TransactionUpdatedEvent extends ApplicationEvent {

    public TransactionUpdatedEvent(Object source) {
        super(source);
    }
}
