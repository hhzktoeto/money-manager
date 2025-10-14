package hhz.ktoeto.moneymanager.feature.transaction.event;

import org.springframework.context.ApplicationEvent;

public class OpenTransactionCreateDialogEvent extends ApplicationEvent {

    public OpenTransactionCreateDialogEvent(Object source) {
        super(source);
    }
}
