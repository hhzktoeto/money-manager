package hhz.ktoeto.moneymanager.feature.transaction.event;

import org.springframework.context.ApplicationEvent;

public class TransactionDeletedEvent extends ApplicationEvent {

    public TransactionDeletedEvent(Object source) {
        super(source);
    }
}
