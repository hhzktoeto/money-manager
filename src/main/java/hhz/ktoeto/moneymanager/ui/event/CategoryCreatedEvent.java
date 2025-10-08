package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class CategoryCreatedEvent extends ApplicationEvent {

    public CategoryCreatedEvent(Object source) {
        super(source);
    }
}
