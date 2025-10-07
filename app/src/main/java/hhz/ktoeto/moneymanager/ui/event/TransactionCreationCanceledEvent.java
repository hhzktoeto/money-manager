package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreationCanceledEvent extends ApplicationEvent {

    public TransactionCreationCanceledEvent(Object source) {
        super(source);
    }
}
