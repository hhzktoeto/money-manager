package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;

@SpringComponent
public class CategoryDataProvider extends ListDataProvider<Category> {

    private final CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        super(new ArrayList<>());
        this.categoryService = categoryService;

    }

    @EventListener(TransactionCreatedEvent.class)
    public void refresh() {
        this.getItems().clear();
        this.getItems().addAll(
                categoryService.getAll(SecurityUtils.getCurrentUserId())
                        .stream()
                        .sorted(Comparator.comparing(Category::getName))
                        .toList()
        );
        this.refreshAll();
    }
}
