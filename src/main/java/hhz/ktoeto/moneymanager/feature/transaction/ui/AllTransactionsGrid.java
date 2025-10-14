package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.component.NoTransactionsImage;
import hhz.ktoeto.moneymanager.core.ui.component.YearMonthPicker;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.ui.data.TransactionDataProvider;
import org.springframework.beans.factory.annotation.Qualifier;

@UIScope
@SpringComponent
public class AllTransactionsGrid extends Composite<VerticalLayout> {

    private final transient FormattingService formattingService;
    private final transient TransactionDataProvider dataProvider;

    public AllTransactionsGrid(FormattingService formattingService,
                               @Qualifier("allTransactionsProvider") TransactionDataProvider dataProvider) {
        this.formattingService = formattingService;
        this.dataProvider = dataProvider;
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();

        HorizontalLayout header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.END,
                LumoUtility.JustifyContent.END
        );
        root.add(header);

        YearMonthPicker yearMonthPicker = new YearMonthPicker(formattingService);
        TransactionFilter currentFilter = dataProvider.getCurrentFilter();
        yearMonthPicker.setYear(currentFilter.getFromDate().getYear());
        yearMonthPicker.setMonth(currentFilter.getFromDate().getMonth());
        yearMonthPicker.addChangeEventHandler((from, to) -> {
            TransactionFilter filter = dataProvider.getCurrentFilter();
            filter.setFromDate(from);
            filter.setToDate(to);
            dataProvider.setFilter(filter);
        });
        header.add(yearMonthPicker);

        Grid<Transaction> grid = new Grid<>();
        grid.addClassNames(LumoUtility.Background.TRANSPARENT);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(transaction -> formattingService.formatDate(transaction.getDate()))
                .setHeader("Дата")
                .setSortable(true)
                .setKey("date");
        grid.addColumn(transaction -> transaction.getCategory().getName())
                .setHeader("Категория")
                .setSortable(true)
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(transaction -> formattingService.formatAmount(transaction.getAmount()))
                .setHeader("Сумма")
                .setSortable(true)
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END);
        grid.setDataProvider(dataProvider);

        NoTransactionsImage noTransactionsImage = new NoTransactionsImage();
        noTransactionsImage.setText("Нет транзакций за выбранный период");
        grid.setEmptyStateComponent(noTransactionsImage);

        root.add(grid);

        return root;
    }
}
