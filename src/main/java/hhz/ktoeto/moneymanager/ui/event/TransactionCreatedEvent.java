package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCreatedEvent extends ApplicationEvent {

    private final transient Transaction transaction;

    public TransactionCreatedEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
