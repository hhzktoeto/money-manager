package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class CategoryDeletedEvent extends ApplicationEvent {

    public CategoryDeletedEvent(Object source) {
        super(source);
    }
}
