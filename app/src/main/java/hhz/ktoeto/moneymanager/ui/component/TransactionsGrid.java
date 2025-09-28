package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@UIScope
@Component
public class TransactionsGrid extends Grid<Transaction> {

    private final TransactionService transactionService;

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendPattern(" yyyy")
            .toFormatter(Locale.of("ru"));

    public TransactionsGrid(TransactionService transactionService) {
        super(Transaction.class, false);
        this.transactionService = transactionService;

        addColumn(transaction -> transaction.getDate().format(FORMATTER)).setHeader("Дата");
        addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория");
        addColumn(transaction -> transaction.getAmount().toString()).setHeader("Сумма");
        setSizeFull();
        setHeightFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        Long userId = SecurityUtils.getCurrentUser().getId();
        setItems(this.transactionService.getAll(userId));
    }
}
