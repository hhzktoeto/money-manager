package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import hhz.ktoeto.moneymanager.core.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

public abstract class AbstractTransactionsDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    protected final transient UserContextHolder userContextHolder;
    protected final transient TransactionService transactionService;

    @Getter
    protected transient TransactionFilter currentFilter;

    protected AbstractTransactionsDataProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.currentFilter = TransactionFilter.currentMonthFilter();
    }

    public void setCurrentFilter(@NonNull TransactionFilter filter) {
        this.currentFilter = filter;
        this.refreshAll();
    }

    protected abstract Stream<Transaction> doFetch(long userId, Query<Transaction, TransactionFilter> query);

    protected abstract int doCount(long userId, Query<Transaction, TransactionFilter> query);

    @Override
    protected Stream<Transaction> fetchFromBackEnd(Query<Transaction, TransactionFilter> query) {
        return this.doFetch(userContextHolder.getCurrentUserId(), query);
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        return this.doCount(userContextHolder.getCurrentUserId(), query);
    }

    @EventListener({
            TransactionCreatedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class
    })
    private void onAnyUpdates() {
        this.refreshAll();
    }
}
