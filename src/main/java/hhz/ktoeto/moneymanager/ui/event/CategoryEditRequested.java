package hhz.ktoeto.moneymanager.ui.event;

import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CategoryEditRequested extends ApplicationEvent {

    private final transient Category category;

    public CategoryEditRequested(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
