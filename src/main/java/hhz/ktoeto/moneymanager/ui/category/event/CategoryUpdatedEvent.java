package hhz.ktoeto.moneymanager.ui.category.event;

import hhz.ktoeto.moneymanager.backend.entity.Category;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CategoryUpdatedEvent extends ApplicationEvent {

    private final Category category;

    public CategoryUpdatedEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
