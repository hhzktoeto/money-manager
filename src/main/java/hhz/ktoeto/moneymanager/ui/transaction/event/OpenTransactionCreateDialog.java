package hhz.ktoeto.moneymanager.ui.transaction.event;

import org.springframework.context.ApplicationEvent;

public class OpenTransactionCreateDialog extends ApplicationEvent {

    public OpenTransactionCreateDialog(Object source) {
        super(source);
    }
}
