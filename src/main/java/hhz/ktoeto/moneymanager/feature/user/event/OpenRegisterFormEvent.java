package hhz.ktoeto.moneymanager.feature.user.event;

import org.springframework.context.ApplicationEvent;

public class OpenRegisterFormEvent extends ApplicationEvent {

    public OpenRegisterFormEvent(Object source) {
        super(source);
    }
}
