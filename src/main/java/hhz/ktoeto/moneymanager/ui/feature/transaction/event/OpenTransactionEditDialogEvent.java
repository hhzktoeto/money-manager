package hhz.ktoeto.moneymanager.ui.feature.transaction.event;

import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OpenTransactionEditDialogEvent extends ApplicationEvent {

    private final transient Transaction transaction;

    public OpenTransactionEditDialogEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
