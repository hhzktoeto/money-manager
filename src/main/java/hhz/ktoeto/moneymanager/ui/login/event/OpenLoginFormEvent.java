package hhz.ktoeto.moneymanager.ui.login.event;

import org.springframework.context.ApplicationEvent;

public class OpenLoginFormEvent extends ApplicationEvent {

    public OpenLoginFormEvent(Object source) {
        super(source);
    }
}
