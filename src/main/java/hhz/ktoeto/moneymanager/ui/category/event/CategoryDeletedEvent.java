package hhz.ktoeto.moneymanager.ui.category.event;

import hhz.ktoeto.moneymanager.backend.entity.Category;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CategoryDeletedEvent extends ApplicationEvent {

    private final transient Category category;

    public CategoryDeletedEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
