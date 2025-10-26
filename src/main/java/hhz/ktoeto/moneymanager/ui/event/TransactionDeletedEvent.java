package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionDeletedEvent extends ApplicationEvent {

    private final transient Transaction transaction;

    public TransactionDeletedEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
