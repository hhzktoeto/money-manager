package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.broadcast.Broadcaster;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.util.Comparator;

@UIScope
@SpringComponent
public class CategoriesCard extends Card {

    private final transient CallbackDataProvider<Category, Void> dataProvider;
    private final transient Broadcaster broadcaster;

    public CategoriesCard(CategoryService categoryService, Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> categoryService.getAll(SecurityUtils.getCurrentUser().getId())
                        .stream()
                        .sorted(Comparator.comparing(Category::getName))
                        .skip(query.getOffset())
                        .limit(query.getLimit()),
                query -> (int) categoryService.count(SecurityUtils.getCurrentUser().getId())
        );

        Grid<Category> categoriesGrid = new Grid<>(Category.class, false);
        categoriesGrid.setWidthFull();
        categoriesGrid.setHeightFull();
        categoriesGrid.addColumn(Category::getName);

        categoriesGrid.setDataProvider(this.dataProvider);
        setSizeFull();
        add(categoriesGrid);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        broadcaster.register(TransactionAddedEvent.class, this::onTransactionAdded);
        broadcaster.register(TransactionDeletedEvent.class, this::onTransactionDeleted);
        broadcaster.register(TransactionUpdatedEvent.class, this::onTransactionUpdated);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcaster.unregister(TransactionUpdatedEvent.class, this::onTransactionUpdated);
        broadcaster.unregister(TransactionDeletedEvent.class, this::onTransactionDeleted);
        broadcaster.unregister(TransactionAddedEvent.class, this::onTransactionAdded);

        super.onDetach(detachEvent);
    }

    private void onTransactionAdded(TransactionAddedEvent ignored) {
        onEvent();
    }

    private void onTransactionDeleted(TransactionDeletedEvent ignored) {
        onEvent();
    }

    private void onTransactionUpdated(TransactionUpdatedEvent ignored) {
        onEvent();
    }

    private void onEvent() {
        getUI().ifPresent(ui -> ui.access(this.dataProvider::refreshAll));
    }
}
