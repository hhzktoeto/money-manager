package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CategoryUpdatedEvent extends ApplicationEvent {

    private final transient Category category;

    public CategoryUpdatedEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
