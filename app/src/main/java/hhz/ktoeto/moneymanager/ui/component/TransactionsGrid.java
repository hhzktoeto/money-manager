package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.entity.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UIScope
@SpringComponent
public class TransactionsGrid extends Grid<Transaction> {

    private final transient CallbackDataProvider<Transaction, Void> dataProvider;

    public TransactionsGrid(TransactionService transactionService) {
        super(Transaction.class, false);
        long userId = SecurityUtils.getCurrentUser().getId();
        this.dataProvider = DataProvider.fromCallbacks(
                query -> {
                    PageRequest pageRequest = PageRequest.of(
                            query.getOffset(),
                            query.getPageSize(),
                            Sort.by(
                                    Sort.Order.desc("date"),
                                    Sort.Order.desc("createdAt")
                            )
                    );

                    return transactionService.getPage(userId, pageRequest).stream();
                },
                query -> (int) transactionService.count(userId)
        );

        this.addColumn(transaction -> FormattingUtils.formatDate(transaction.getDate())).setHeader("Дата");
        this.addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория");
        this.addColumn(transaction -> FormattingUtils.formatAmount(transaction.getAmount())).setHeader("Сумма");

        this.setMultiSort(true);
        this.setDataProvider(dataProvider);
        this.setSizeFull();
    }

    @EventListener({
            TransactionAddedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class}
    )
    private void onTransactionUpdate(TransactionAddedEvent ignored) {
        UI.getCurrent().access(this.dataProvider::refreshAll);
    }
}
