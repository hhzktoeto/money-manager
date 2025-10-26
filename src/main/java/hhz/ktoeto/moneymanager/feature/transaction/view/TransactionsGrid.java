package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionsGrid extends Composite<VerticalLayout> implements TransactionsGridView {

    private final transient TransactionsGridViewPresenter presenter;
    private final Mode mode;

    private final RussianDateRangePicker dateRangePicker;
    private final Grid<Transaction> grid;

    public enum Mode {
        RECENT,
        ALL
    }

    public TransactionsGrid(TransactionsGridViewPresenter presenter, Mode mode) {
        this.mode = mode;
        this.presenter = presenter;
        this.presenter.setView(this);

        this.dateRangePicker = new RussianDateRangePicker("Период");
        this.grid = new Grid<>();
        this.grid.setDataProvider(this.presenter.getTransactionsProvider());
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();

        Div header = new Div();
        this.configureHeader(header);
        root.add(header);

        this.configureGrid(this.grid);

        root.add(this.grid);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureGrid(Grid<Transaction> grid) {
        grid.addClassNames(LumoUtility.Background.TRANSPARENT);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
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

        Grid.Column<Transaction> categoryDateColumn = grid.addColumn(new TransactionCategoryDateRenderer())
                .setKey("date")
                .setSortable(mode == Mode.ALL)
                .setHeader(mode == Mode.ALL ? "По дате" : null);
        grid.addColumn(new NumberRenderer<>(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault())))
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END)
                .setSortable(mode == Mode.ALL)
                .setHeader(mode == Mode.ALL ? "По сумме" : null)
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("amount-column ");
                    switch (transaction.getType()) {
                        case EXPENSE -> stringBuilder.append("expense");
                        case INCOME -> stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });

        if (mode == Mode.ALL) {
            grid.sort(Collections.singletonList(
                    new GridSortOrder<>(categoryDateColumn, SortDirection.DESCENDING))
            );
        }
    }

    //TODO: вот это надо рефакторить
    private void configureHeader(Div header) {
        MultiSelectComboBox<Category> categoryMultiSelect = new MultiSelectComboBox<>("Категории");
        categoryMultiSelect.setItemLabelGenerator(Category::getName);
        categoryMultiSelect.setClearButtonVisible(true);
        categoryMultiSelect.addValueChangeListener(event -> {
            TransactionFilter filter = presenter.getFilter();
            Set<Long> selectedCategoriesIds = event.getValue().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            filter.setCategoriesIds(selectedCategoriesIds);
            presenter.setFilter(filter);
        });
        categoryMultiSelect.setItems(presenter.getCategoriesProvider());

        RadioButtonGroup<String> typeGroup = new RadioButtonGroup<>("Тип");
        typeGroup.setItems("Все", "Доход", "Расход");
        typeGroup.addValueChangeListener(event -> {
            TransactionFilter filter = presenter.getFilter();
            Transaction.Type type = switch (event.getValue()) {
                case "Доход" -> Transaction.Type.INCOME;
                case "Расход" -> Transaction.Type.EXPENSE;
                default -> null;
            };
            filter.setType(type);
            presenter.setFilter(filter);
        });
        typeGroup.setValue("Все");

        TransactionFilter transactionFilter = this.presenter.getFilter();
        this.dateRangePicker.setValue(new DateRange(transactionFilter.getFromDate(), transactionFilter.getToDate()));
        this.dateRangePicker.addValueChangeListener(event -> {
            TransactionFilter filter = this.presenter.getFilter();
            DateRange selectedRange = this.dateRangePicker.getValue();
            filter.setFromDate(selectedRange.getStartDate());
            filter.setToDate(selectedRange.getEndDate());
            this.presenter.setFilter(filter);
            this.dateRangePicker.suppressKeyboard();
        });

        header.setVisible(mode == Mode.ALL);
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.SMALL,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.Grid.Breakpoint.Medium.COLUMNS_3,
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.AlignContent.START
        );
        header.add(typeGroup, categoryMultiSelect, this.dateRangePicker);
    }

    private static final class TransactionCategoryDateRenderer extends ComponentRenderer<HorizontalLayout, Transaction> {

        //TODO: Добавить иконки для категория, когда они появятся
        private TransactionCategoryDateRenderer() {
            super(transaction -> {
                HorizontalLayout layout = new HorizontalLayout();
                Category transactionCategory = transaction.getCategory();
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("dd MMMM yyyy")
                        .toFormatter()
                        .withLocale(Locale.of("ru"));

                Span categoryName = new Span(transactionCategory.getName());
                categoryName.addClassNames(
                        LumoUtility.FontWeight.BOLD,
                        LumoUtility.FontSize.MEDIUM
                );
                Span transactionDate = new Span(formatter.format(transaction.getDate()));
                transactionDate.addClassNames(
                        LumoUtility.TextColor.SECONDARY,
                        LumoUtility.FontWeight.LIGHT,
                        LumoUtility.FontSize.SMALL
                );

                VerticalLayout nameDateLayout = new VerticalLayout(categoryName, transactionDate);
                nameDateLayout.addClassName(LumoUtility.Gap.XSMALL);

                layout.add(nameDateLayout);
                return layout;
            });
        }
    }
}
