package hhz.ktoeto.moneymanager.ui.feature.transaction.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.ui.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

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
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();

        if (this.mode == Mode.ALL) {
            HorizontalLayout header = new HorizontalLayout();
            this.configureHeader(header);
            root.add(header);
        }

        this.configureGrid(this.grid);

        root.add(this.grid);

        return root;
    }

    @Override
    public void updateItems(List<Transaction> transactions) {
        this.grid.setItems(transactions);
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

        EmptyDataImage noTransactionsImage = new EmptyDataImage();
        noTransactionsImage.setText(mode == Mode.RECENT
                ? "Нет недавних транзакций"
                : "Нет транзакций за выбранный период");
        grid.setEmptyStateComponent(noTransactionsImage);

        grid.addItemClickListener(event -> presenter.onEditRequested(event.getItem()));
        grid.addColumn(new TransactionCategoryDateRenderer());
        grid.addColumn(new NumberRenderer<>(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault())))
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END)
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("amount-column ");
                    switch (transaction.getType()) {
                        case EXPENSE -> stringBuilder.append("expense");
                        case INCOME -> stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });
    }

    private void configureHeader(HorizontalLayout header) {
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.END,
                LumoUtility.JustifyContent.END
        );

        TransactionFilter transactionFilter = this.presenter.getCurrentFilter();
        this.dateRangePicker.setValue(new DateRange(transactionFilter.getFromDate(), transactionFilter.getToDate()));
        this.dateRangePicker.addValueChangeListener(event -> {
            TransactionFilter filter = this.presenter.getCurrentFilter();
            DateRange selectedRange = this.dateRangePicker.getValue();
            filter.setFromDate(selectedRange.getStartDate());
            filter.setToDate(selectedRange.getEndDate());
            this.presenter.setCurrentFilter(filter);
            this.dateRangePicker.suppressKeyboard();
        });
        header.add(this.dateRangePicker);
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
