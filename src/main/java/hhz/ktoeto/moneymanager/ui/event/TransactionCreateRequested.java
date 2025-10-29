package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreateRequested extends ApplicationEvent {

    public TransactionCreateRequested(Object source) {
        super(source);
    }
}
