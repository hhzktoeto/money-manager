package hhz.ktoeto.moneymanager.ui.feature.category.event;

import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CategoryCreatedEvent extends ApplicationEvent {

    private final transient Category category;

    public CategoryCreatedEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
