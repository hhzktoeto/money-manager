package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.backend.dto.TransactionFilter;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.ui.component.NoTransactionsImage;
import hhz.ktoeto.moneymanager.ui.component.PeriodPicker;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import org.springframework.beans.factory.annotation.Qualifier;

@UIScope
@SpringComponent
public class AllTransactionsGrid extends Composite<VerticalLayout> {

    private final transient TransactionDataProvider dataProvider;

    public AllTransactionsGrid(@Qualifier("allTransactionsProvider") TransactionDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        Grid<Transaction> grid = new Grid<>();
        grid.addClassNames(LumoUtility.Background.TRANSPARENT);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        NoTransactionsImage noTransactionsImage = new NoTransactionsImage();
        noTransactionsImage.setText("Нет транзакций за выбранный период");
        grid.setEmptyStateComponent(noTransactionsImage);

        grid.addColumn(transaction -> FormattingUtils.formatDate(transaction.getDate()))
                .setHeader("Дата")
                .setSortable(true)
                .setKey("date");
        grid.addColumn(transaction -> transaction.getCategory().getName())
                .setHeader("Категория")
                .setSortable(true)
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(transaction -> FormattingUtils.formatAmount(transaction.getAmount()))
                .setHeader("Сумма")
                .setSortable(true)
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END);

        grid.setDataProvider(dataProvider);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.END,
                LumoUtility.JustifyContent.END
        );

        PeriodPicker periodPicker = new PeriodPicker();
        periodPicker.onFromDateChange(event -> {
            TransactionFilter filter = dataProvider.getCurrentFilter();
            filter.setFromDate(periodPicker.fromDate());
            dataProvider.setFilter(filter);
        });
        periodPicker.onToDateChange(event -> {
            TransactionFilter filter = dataProvider.getCurrentFilter();
            filter.setToDate(periodPicker.toDate());
            dataProvider.setFilter(filter);
        });
        header.add(periodPicker);

        root.add(header, grid);

        return root;
    }
}
