package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@UIScope
@SpringComponent
public class TransactionsGrid extends Grid<Transaction> {

    private final transient CallbackDataProvider<Transaction, Void> dataProvider;

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendPattern(" yyyy")
            .toFormatter(Locale.of("ru"));

    public TransactionsGrid(TransactionService transactionService) {
        super(Transaction.class, false);
        this.dataProvider = DataProvider.fromCallbacks(
                query -> transactionService.getAll(SecurityUtils.getCurrentUser().getId())
                        .stream()
                        .skip(query.getOffset())
                        .limit(query.getLimit()),
                query -> (int) transactionService.count(SecurityUtils.getCurrentUser().getId())
        );

        addColumn(transaction -> transaction.getDate().format(FORMATTER)).setHeader("Дата");
        addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория");
        addColumn(transaction -> transaction.getAmount().toString()).setHeader("Сумма");

        setDataProvider(dataProvider);
        setSizeFull();
    }

    public void refreshTransactions() {
        this.dataProvider.refreshAll();
    }
}
