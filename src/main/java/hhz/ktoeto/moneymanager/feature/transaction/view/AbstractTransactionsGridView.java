package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.view.renderer.TransactionCategoryDateRenderer;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class AbstractTransactionsGridView extends Composite<VerticalLayout> implements TransactionsGridView {

    protected final transient TransactionsGridViewPresenter presenter;

    protected final Grid<Transaction> grid;

    public AbstractTransactionsGridView(TransactionsGridViewPresenter presenter) {
        this.presenter = presenter;

        this.grid = new Grid<>();
    }

    protected abstract String getEmptyStateText();

    protected abstract boolean isSortable();

    protected abstract void configurePagination(Grid<Transaction> grid);

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setPadding(false);
        root.setSpacing(false);
        root.setSizeFull();

        this.configureGrid();

        root.add(this.grid);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureGrid() {
        this.grid.setDataProvider(this.presenter.getTransactionsProvider());
        this.grid.addClassNames(LumoUtility.Background.TRANSPARENT);
        this.grid.addThemeVariants(
                GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS
        );

        this.configurePagination(this.grid);

        this.grid.setSelectionMode(Grid.SelectionMode.NONE);
        this.grid.addItemClickListener(event -> this.presenter.onEditRequested(event.getItem()));

        EmptyDataImage noTransactionsImage = new EmptyDataImage();
        noTransactionsImage.setText(this.getEmptyStateText());
        this.grid.setEmptyStateComponent(noTransactionsImage);

        this.grid.addColumn(new TransactionCategoryDateRenderer())
                .setKey("date")
                .setSortable(this.isSortable());
        this.grid.addColumn(new NumberRenderer<>(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault())))
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END)
                .setSortable(this.isSortable())
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("amount-column ");
                    switch (transaction.getType()) {
                        case EXPENSE -> stringBuilder.append("expense");
                        case INCOME -> stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });
    }
}
