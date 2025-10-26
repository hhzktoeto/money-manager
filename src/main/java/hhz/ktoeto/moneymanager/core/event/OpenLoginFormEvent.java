package hhz.ktoeto.moneymanager.core.event;

import org.springframework.context.ApplicationEvent;

public class OpenLoginFormEvent extends ApplicationEvent {

    public OpenLoginFormEvent(Object source) {
        super(source);
    }
}
