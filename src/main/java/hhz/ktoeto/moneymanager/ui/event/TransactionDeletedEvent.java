package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class TransactionDeletedEvent extends ApplicationEvent {

    public TransactionDeletedEvent(Object source) {
        super(source);
    }
}
