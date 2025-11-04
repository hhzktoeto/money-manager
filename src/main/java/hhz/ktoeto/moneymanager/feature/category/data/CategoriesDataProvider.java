package hhz.ktoeto.moneymanager.feature.category.data;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.core.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.CategoryDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.CategoryUpdatedEvent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;

@Getter(AccessLevel.PROTECTED)
public abstract class CategoriesDataProvider extends ListDataProvider<Category> {

    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;

    protected CategoriesDataProvider(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(new ArrayList<>());
        this.categoryService = categoryService;
        this.userContextHolder = userContextHolder;
    }

    protected abstract void loadData(long userId);

    @PostConstruct
    private void doLoadData() {
        this.getItems().clear();
        this.loadData(this.userContextHolder.getCurrentUserId());
        this.refreshAll();
    }

    @EventListener({
            CategoryCreatedEvent.class,
            CategoryUpdatedEvent.class,
            CategoryDeletedEvent.class
    })
    private void onAnyUpdates() {
        this.doLoadData();
    }
}
