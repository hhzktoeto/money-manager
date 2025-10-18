package hhz.ktoeto.moneymanager.ui.feature.category.event;

import org.springframework.context.ApplicationEvent;

public class OpenCategoryCreateDialogEvent extends ApplicationEvent {

    public OpenCategoryCreateDialogEvent(Object source) {
        super(source);
    }
}
