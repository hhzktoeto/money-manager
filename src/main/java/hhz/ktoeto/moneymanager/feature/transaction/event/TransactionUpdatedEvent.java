package hhz.ktoeto.moneymanager.feature.transaction.event;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionUpdatedEvent extends ApplicationEvent {

    private final transient Transaction transaction;

    public TransactionUpdatedEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
