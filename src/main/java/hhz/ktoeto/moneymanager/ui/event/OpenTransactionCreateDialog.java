package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class OpenTransactionCreateDialog extends ApplicationEvent {

    public OpenTransactionCreateDialog(Object source) {
        super(source);
    }
}
