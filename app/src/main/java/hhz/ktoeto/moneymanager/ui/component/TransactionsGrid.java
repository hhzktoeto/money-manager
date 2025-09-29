package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.broadcast.Broadcaster;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionUpdatedEvent;
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
    private final transient Broadcaster broadcaster;

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendPattern(" yyyy")
            .toFormatter(Locale.of("ru"));

    public TransactionsGrid(TransactionService transactionService, Broadcaster broadcaster) {
        super(Transaction.class, false);
        this.broadcaster = broadcaster;
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
