package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.mixin.HasFilter;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AllTransactionsGridView extends TransactionsGridView implements HasUpdatableData<TransactionsSummaries> {

    private final HasFilter<TransactionFilter> hasFilterDelegate;

    private final Details gridSettings;
    private final Button expensesFilterButton;
    private final Button incomesFilterButton;
    private final Button totalFilterButton;

    public AllTransactionsGridView(AllTransactionsGridPresenter presenter) {
        super(presenter);
        this.hasFilterDelegate = presenter;

        this.gridSettings = new Details("Настройки");
        this.expensesFilterButton = new Button();
        this.incomesFilterButton = new Button();
        this.totalFilterButton = new Button();
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = super.initContent();

        this.configureGridHeader();
        this.configureGridSettings();

        root.addComponentAsFirst(gridSettings);

        return root;
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет транзакций за выбранный период";
    }

    @Override
    protected boolean isSortable() {
        return true;
    }

    @Override
    protected void configurePagination(Grid<Transaction> grid) {
        grid.setPageSize(25);
    }

    @Override
    public void update(TransactionsSummaries summaries) {
        expensesFilterButton.setText(this.presenter.formatAmount(summaries.expenses()));
        incomesFilterButton.setText(this.presenter.formatAmount(summaries.incomes()));
        totalFilterButton.setText(this.presenter.formatAmount(summaries.total()));
    }

    private void configureGridHeader() {
        Grid.Column<Transaction> categoryDateColumn = this.grid.getColumnByKey("date")
                .setHeader("По дате");
        Grid.Column<Transaction> amountColumn = this.grid.getColumnByKey("amount")
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
            TransactionFilter filter = this.hasFilterDelegate.getFilter();
            Transaction.Type effectiveType = filter.getType() == Transaction.Type.EXPENSE
                    ? null
                    : Transaction.Type.EXPENSE;
            filter.setType(effectiveType);
            this.hasFilterDelegate.setFilter(filter);
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
            TransactionFilter filter = this.hasFilterDelegate.getFilter();
            Transaction.Type effectiveType = filter.getType() == Transaction.Type.INCOME
                    ? null
                    : Transaction.Type.INCOME;
            filter.setType(effectiveType);
            this.hasFilterDelegate.setFilter(filter);
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
            TransactionFilter filter = this.hasFilterDelegate.getFilter();
            filter.setType(null);
            this.hasFilterDelegate.setFilter(filter);
        });

        Div summariesDiv = new Div(this.expensesFilterButton, this.incomesFilterButton, this.totalFilterButton);
        summariesDiv.addClassNames(
                LumoUtility.Display.GRID,
                LumoUtility.Grid.Column.COLUMNS_3,
                LumoUtility.Width.FULL,
                LumoUtility.Padding.NONE,
                LumoUtility.Margin.NONE
        );

        HeaderRow summariesRow = this.grid.prependHeaderRow();
        summariesRow.join(categoryDateColumn, amountColumn).setComponent(summariesDiv);

        this.grid.sort(Collections.singletonList(
                new GridSortOrder<>(categoryDateColumn, SortDirection.DESCENDING))
        );
    }

    private void configureGridSettings() {
        MultiSelectComboBox<Category> categoryMultiSelect = new MultiSelectComboBox<>("Категории");
        categoryMultiSelect.setItemLabelGenerator(Category::getName);
        categoryMultiSelect.setClearButtonVisible(true);
        categoryMultiSelect.addValueChangeListener(event -> {
            TransactionFilter filter = this.hasFilterDelegate.getFilter();
            Set<Long> selectedCategoriesIds = event.getValue().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            filter.setCategoriesIds(selectedCategoriesIds);
            this.hasFilterDelegate.setFilter(filter);
        });
        categoryMultiSelect.setItems(this.presenter.getCategoriesProvider());

        RussianDateRangePicker dateRangePicker = new RussianDateRangePicker("Период");
        TransactionFilter transactionFilter = this.hasFilterDelegate.getFilter();
        dateRangePicker.setValue(new DateRange(transactionFilter.getFromDate(), transactionFilter.getToDate()));
        dateRangePicker.addValueChangeListener(event -> {
            TransactionFilter filter = this.hasFilterDelegate.getFilter();
            DateRange selectedRange = dateRangePicker.getValue();
            filter.setFromDate(selectedRange.getStartDate());
            filter.setToDate(selectedRange.getEndDate());
            this.hasFilterDelegate.setFilter(filter);
            dateRangePicker.suppressKeyboard();
        });

        Div content = new Div(categoryMultiSelect, dateRangePicker);
        content.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.Column.SMALL,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2
        );

        this.gridSettings.setWidthFull();
        this.gridSettings.add(content);
    }
}
