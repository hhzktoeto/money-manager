package hhz.ktoeto.moneymanager.ui.event;

import org.springframework.context.ApplicationEvent;

public class CategoryCreateRequested extends ApplicationEvent {

    public CategoryCreateRequested(Object source) {
        super(source);
    }
}
