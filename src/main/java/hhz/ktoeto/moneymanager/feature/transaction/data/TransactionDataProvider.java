package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.core.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionUpdatedEvent;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

public class TransactionDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    private static final int UNLIMITED = Integer.MAX_VALUE;

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;
    @Getter
    protected transient TransactionFilter currentFilter;

    private int maxSize;

    public TransactionDataProvider(TransactionService transactionService, UserContextHolder userContextHolder, int maxSize) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.currentFilter = TransactionFilter.currentMonthFilter();
        this.maxSize = maxSize;
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
        boolean isLimited = maxSize < UNLIMITED;
        if (isLimited || query.getSortOrders().isEmpty()) {
            sort = Sort.by(Sort.Order.desc("date"))
                    .and(Sort.by(Sort.Order.desc("createdAt")));
        } else {
            sort = query.getSortOrders().stream()
                    .map(order -> Sort.by(order.getDirection() == SortDirection.DESCENDING
                                    ? Sort.Direction.DESC
                                    : Sort.Direction.ASC,
                            order.getSorted())
                    )
                    .reduce(Sort.unsorted(), Sort::and);
        }

        int limit = query.getLimit();
        int page = query.getOffset() / query.getLimit();
        if (isLimited) {
            page = 0;
            limit = Math.min(limit, maxSize);
        }
        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        return transactionService.getPage(userId, filter, pageRequest).getContent().stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);
        int count = transactionService.count(userId, filter);

        return Math.min(maxSize, count);
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
