package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UIScope
@SpringComponent
public class CategoriesCard extends Card {

    private final transient CallbackDataProvider<Category, Void> dataProvider;

    public CategoriesCard(CategoryService categoryService) {
        long userId = SecurityUtils.getCurrentUser().getId();
        this.dataProvider = DataProvider.fromCallbacks(
                query -> {
                    PageRequest pageRequest = PageRequest.of(
                            query.getOffset(),
                            query.getPageSize(),
                            Sort.by(Sort.Order.asc("name"))
                    );
                    return categoryService.getPage(userId, pageRequest).stream();
                },
                query -> (int) categoryService.count(userId)
        );

        Grid<Category> categoriesGrid = new Grid<>(Category.class, false);
        categoriesGrid.setWidthFull();
        categoriesGrid.setHeightFull();
        categoriesGrid.addColumn(Category::getName);

        categoriesGrid.setDataProvider(this.dataProvider);
        setSizeFull();
        add(categoriesGrid);
    }

    @EventListener({
            TransactionAddedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class}
    )
    private void onTransactionUpdate(TransactionAddedEvent ignored) {
        UI.getCurrent().access(this.dataProvider::refreshAll);
    }
}
