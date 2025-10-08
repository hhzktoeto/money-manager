package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.ui.event.CategoryDeletedEvent;
import hhz.ktoeto.moneymanager.ui.event.CategoryUpdatedEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringComponent
@VaadinSessionScope
public class CategoryDataProvider extends ListDataProvider<Category> {

    private final CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        super(new ArrayList<>());
        this.categoryService = categoryService;
    }

    @PostConstruct
    private void loadData() {
        VaadinSession.getCurrent().getUIs().forEach(ui -> ui.access(() -> {
            this.getItems().clear();
            this.getItems().addAll(
                    categoryService.getAll(SecurityUtils.getCurrentUserId())
                            .stream()
                            .sorted(Comparator.comparing(Category::getName))
                            .collect(Collectors.toList())
            );
            this.refreshAll();
        }));
    }

    @EventListener(CategoryCreatedEvent.class)
    private void onCategoryCreated(CategoryCreatedEvent event) {
        this.getItems().add(event.getCategory());
        this.refreshAll();
    }

    @EventListener(CategoryDeletedEvent.class)
    private void onCategoryDeleted(CategoryDeletedEvent event) {
        this.getItems().remove(event.getCategory());
        this.refreshAll();
    }

    @EventListener(CategoryUpdatedEvent.class)
    private void onCategoryUpdated(CategoryUpdatedEvent event) {
        Category oldCategory = this.getItems().stream()
                .filter(category -> Objects.equals(category.getId(), event.getCategory().getId()))
                .findFirst()
                .orElse(null);
        if (oldCategory != null) {
            this.getItems().remove(oldCategory);
            this.getItems().add(event.getCategory());
            this.refreshAll();
        }
    }
}
