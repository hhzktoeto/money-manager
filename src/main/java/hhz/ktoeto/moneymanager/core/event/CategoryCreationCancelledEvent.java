package hhz.ktoeto.moneymanager.core.event;

import org.springframework.context.ApplicationEvent;

public class CategoryCreationCancelledEvent extends ApplicationEvent {

    public CategoryCreationCancelledEvent(Object source) {
        super(source);
    }
}
