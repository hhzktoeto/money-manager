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
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@SpringComponent
@VaadinSessionScope
public class CategoryDataProvider extends ListDataProvider<Category> {

    private final CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        super(new ArrayList<>());
        this.categoryService = categoryService;
    }

    @EventListener({
            CategoryCreatedEvent.class,
            CategoryDeletedEvent.class,
            CategoryUpdatedEvent.class
    })
    private void refresh() {
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
}
