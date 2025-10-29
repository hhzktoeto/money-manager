package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.feature.transaction.view.renderer.TransactionCategoryDateRenderer;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

public class TransactionsGrid extends Composite<VerticalLayout> implements TransactionsGridView {

    private final transient TransactionsGridViewPresenter presenter;
    private final Mode mode;

    private final Button expensesFilterButton;
    private final Button incomesFilterButton;
    private final Button totalFilterButton;

    public TransactionsGrid(TransactionsGridViewPresenter presenter, Mode mode) {
        this.mode = mode;
        this.presenter = presenter;

        this.expensesFilterButton = new Button();
        this.incomesFilterButton = new Button();
        this.totalFilterButton = new Button();

        this.presenter.initialize(this);
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setPadding(false);
        root.setSpacing(false);
        root.setSizeFull();

        Grid<Transaction> grid = new Grid<>();

        this.configureGrid(grid);

        if (mode == Mode.ALL) {
            this.configureGridHeader(grid);
        }

        root.add(grid);

        return root;
    }

    @Override
    public void updateSummaries(TransactionsSummaries summaries) {
        expensesFilterButton.setText(this.presenter.formatAmount(summaries.expenses()));
        incomesFilterButton.setText(this.presenter.formatAmount(summaries.incomes()));
        totalFilterButton.setText(this.presenter.formatAmount(summaries.total()));
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureGrid(Grid<Transaction> grid) {
        grid.setDataProvider(this.presenter.getTransactionsProvider());
        grid.addClassNames(LumoUtility.Background.TRANSPARENT);
        grid.addThemeVariants(
                GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS
        );
        if (mode == Mode.RECENT) {
            grid.setAllRowsVisible(true);
        } else {
            grid.setPageSize(25);
        }
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> presenter.onEditRequested(event.getItem()));

        EmptyDataImage noTransactionsImage = new EmptyDataImage();
        noTransactionsImage.setText(mode == Mode.RECENT
                ? "Нет недавних транзакций"
                : "Нет транзакций за выбранный период");
        grid.setEmptyStateComponent(noTransactionsImage);

        grid.addColumn(new TransactionCategoryDateRenderer())
                .setKey("date")
                .setSortable(mode == Mode.ALL);
        grid.addColumn(new NumberRenderer<>(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault())))
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END)
                .setSortable(mode == Mode.ALL)
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("amount-column ");
                    switch (transaction.getType()) {
                        case EXPENSE -> stringBuilder.append("expense");
                        case INCOME -> stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });
    }

    private void configureGridHeader(Grid<Transaction> grid) {
        Grid.Column<Transaction> categoryDateColumn = grid.getColumnByKey("date")
                .setHeader("По дате");
        Grid.Column<Transaction> amountColumn = grid.getColumnByKey("amount")
                .setHeader("По сумме");

        this.expensesFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.expensesFilterButton.getStyle().set(StyleConstants.BORDER_RADIUS, StyleConstants.BorderRadius.LEFT_075REM);
        this.expensesFilterButton.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.FontSize.XSMALL,
                LumoUtility.FontSize.Breakpoint.Small.MEDIUM,
                LumoUtility.TextColor.ERROR
        );
        this.expensesFilterButton.addClickListener(e -> {
            TransactionFilter filter = this.presenter.getFilter();
            Transaction.Type effectiveType = filter.getType() == Transaction.Type.EXPENSE
                    ? null
                    : Transaction.Type.EXPENSE;
            filter.setType(effectiveType);
            this.presenter.setFilter(filter);
        });

        this.incomesFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.incomesFilterButton.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.BorderRadius.NONE,
                LumoUtility.FontSize.XSMALL,
                LumoUtility.FontSize.Breakpoint.Small.MEDIUM,
                LumoUtility.TextColor.SUCCESS
        );
        this.incomesFilterButton.addClickListener(e -> {
            TransactionFilter filter = this.presenter.getFilter();
            Transaction.Type effectiveType = filter.getType() == Transaction.Type.INCOME
                    ? null
                    : Transaction.Type.INCOME;
            filter.setType(effectiveType);
            this.presenter.setFilter(filter);
        });

        this.totalFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.totalFilterButton.getStyle().set(StyleConstants.BORDER_RADIUS, StyleConstants.BorderRadius.RIGHT_075REM);
        this.totalFilterButton.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.BorderRadius.NONE,
                LumoUtility.FontSize.XSMALL,
                LumoUtility.FontSize.Breakpoint.Small.MEDIUM,
                LumoUtility.TextColor.BODY
        );
        this.totalFilterButton.addClickListener(e -> {
            TransactionFilter filter = this.presenter.getFilter();
            filter.setType(null);
            this.presenter.setFilter(filter);
        });

        Div summariesDiv = new Div(this.expensesFilterButton, this.incomesFilterButton, this.totalFilterButton);
        summariesDiv.addClassNames(
                LumoUtility.Display.GRID,
                LumoUtility.Grid.Column.COLUMNS_3,
                LumoUtility.Width.FULL,
                LumoUtility.Padding.NONE,
                LumoUtility.Margin.NONE
        );

        HeaderRow summariesRow = grid.prependHeaderRow();
        summariesRow.join(categoryDateColumn, amountColumn).setComponent(summariesDiv);

        grid.sort(Collections.singletonList(
                new GridSortOrder<>(categoryDateColumn, SortDirection.DESCENDING))
        );
    }

    public enum Mode {RECENT, ALL}
}
