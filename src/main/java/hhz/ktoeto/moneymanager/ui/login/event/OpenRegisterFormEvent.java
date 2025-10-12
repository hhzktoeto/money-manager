package hhz.ktoeto.moneymanager.ui.login.event;

import org.springframework.context.ApplicationEvent;

public class OpenRegisterFormEvent extends ApplicationEvent {

    public OpenRegisterFormEvent(Object source) {
        super(source);
    }
}
