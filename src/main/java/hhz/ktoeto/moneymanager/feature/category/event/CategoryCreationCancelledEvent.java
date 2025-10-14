package hhz.ktoeto.moneymanager.feature.category.event;

import org.springframework.context.ApplicationEvent;

public class CategoryCreationCancelledEvent extends ApplicationEvent {

    public CategoryCreationCancelledEvent(Object source) {
        super(source);
    }
}
