package hhz.ktoeto.moneymanager.ui.category.event;

import org.springframework.context.ApplicationEvent;

public class OpenCategoryCreateDialog extends ApplicationEvent {

    public OpenCategoryCreateDialog(Object source) {
        super(source);
    }
}
