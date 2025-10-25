package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class CategoryCreationCancelledEvent extends ApplicationEvent {

    public CategoryCreationCancelledEvent(Object source) {
        super(source);
    }
}
