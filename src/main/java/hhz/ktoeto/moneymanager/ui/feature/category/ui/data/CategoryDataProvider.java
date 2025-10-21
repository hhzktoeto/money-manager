package hhz.ktoeto.moneymanager.ui.feature.category.ui.data;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.feature.category.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.ui.feature.category.event.CategoryDeletedEvent;
import hhz.ktoeto.moneymanager.ui.feature.category.event.CategoryUpdatedEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringComponent
@VaadinSessionScope
public class CategoryDataProvider extends ListDataProvider<Category> {

    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;

    public CategoryDataProvider(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(new ArrayList<>());
        this.categoryService = categoryService;
        this.userContextHolder = userContextHolder;
    }

    @PostConstruct
    private void loadData() {
        this.getItems().clear();
        this.getItems().addAll(
                categoryService.getAll(userContextHolder.getCurrentUserId())
                        .stream()
                        .sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
                        .collect(Collectors.toList())
        );
        this.refreshAll();
    }

    @EventListener(CategoryCreatedEvent.class)
    private void onCategoryCreated(CategoryCreatedEvent event) {
        List<Category> items = (List<Category>) this.getItems();
        items.add(event.getCategory());
        items.sort(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER));
        this.refreshAll();
    }

    @EventListener(CategoryDeletedEvent.class)
    private void onCategoryDeleted(CategoryDeletedEvent event) {
        List<Category> items = (List<Category>) this.getItems();
        items.remove(event.getCategory());
        items.sort(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER));
        this.refreshAll();
    }

    @EventListener(CategoryUpdatedEvent.class)
    private void onCategoryUpdated(CategoryUpdatedEvent event) {
        List<Category> items = (List<Category>) this.getItems();
        Category oldCategory = items.stream()
                .filter(category -> Objects.equals(category.getId(), event.getCategory().getId()))
                .findFirst()
                .orElse(null);
        if (oldCategory != null) {
            items.remove(oldCategory);
            items.add(event.getCategory());
            items.sort(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER));
            this.refreshAll();
        }
    }
}
