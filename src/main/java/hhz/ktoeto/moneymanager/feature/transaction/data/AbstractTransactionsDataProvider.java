package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import hhz.ktoeto.moneymanager.core.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractTransactionsDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    protected final transient UserContextHolder userContextHolder;
    protected final transient TransactionService transactionService;

    @Getter
    protected transient TransactionFilter currentFilter;

    protected Integer customLimit;
    protected Sort customSort;

    public AbstractTransactionsDataProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.currentFilter = TransactionFilter.currentMonthFilter();
    }

    public AbstractTransactionsDataProvider(UserContextHolder userContextHolder, TransactionService transactionService,
                                            Integer limit, Sort sort) {
        this(userContextHolder, transactionService);
        this.customLimit = limit;
        this.customSort = sort;
    }

    public void setCurrentFilter(@NonNull TransactionFilter filter) {
        this.currentFilter = filter;
        this.refreshAll();
    }

    @Override
    protected Stream<Transaction> fetchFromBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);

        Sort sort;

        if (customSort != null) {
            sort = customSort;
        } else {
            sort = query.getSortOrders().stream()
                    .map(order -> Sort.by(order.getDirection() == SortDirection.DESCENDING
                                    ? Sort.Direction.DESC
                                    : Sort.Direction.ASC,
                            order.getSorted())
                    )
                    .reduce(Sort.unsorted(), Sort::and)
                    .and(Sort.by(Sort.Direction.DESC, "createdAt"));

            if (Objects.equals(Sort.unsorted(), sort)) {
                sort = Sort.by(Sort.Direction.DESC, "date")
                        .and(Sort.by(Sort.Direction.DESC, "createdAt"));
            }
        }

        int limit = query.getLimit();
        int page = query.getOffset() / query.getLimit();

        if (customLimit != null) {
            limit = customLimit;
            page = 0;
        }

        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        return transactionService.getPage(userId, filter, pageRequest).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);
        int count = transactionService.count(userId, filter);

        return Objects.requireNonNullElse(customLimit, count);
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
