package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionEditRequested extends ApplicationEvent {

    private final Transaction transaction;

    public TransactionEditRequested(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
