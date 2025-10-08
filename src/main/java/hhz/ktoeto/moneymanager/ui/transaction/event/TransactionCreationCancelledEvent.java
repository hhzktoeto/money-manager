package hhz.ktoeto.moneymanager.ui.transaction.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreationCancelledEvent extends ApplicationEvent {

    public TransactionCreationCancelledEvent(Object source) {
        super(source);
    }
}
