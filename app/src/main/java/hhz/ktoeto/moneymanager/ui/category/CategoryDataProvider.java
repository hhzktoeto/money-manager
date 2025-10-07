package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.transaction.entity.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;

@SpringComponent
public class CategoryDataProvider extends ListDataProvider<Category> implements VaadinServiceInitListener {

    private final CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        super(new ArrayList<>());
        this.categoryService = categoryService;

    }

    @EventListener(TransactionCreatedEvent.class)
    private void refresh() {
        this.getItems().clear();
        this.getItems().addAll(
                categoryService.getAll(SecurityUtils.getCurrentUser().getId())
                        .stream()
                        .sorted(Comparator.comparing(Category::getName))
                        .toList()
        );
        this.refreshAll();
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addSessionInitListener(initEvent -> this.refresh());
    }
}
