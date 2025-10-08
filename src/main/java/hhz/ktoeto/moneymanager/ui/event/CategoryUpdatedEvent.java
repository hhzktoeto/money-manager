package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class CategoryUpdatedEvent extends ApplicationEvent {

    public CategoryUpdatedEvent(Object source) {
        super(source);
    }
}
