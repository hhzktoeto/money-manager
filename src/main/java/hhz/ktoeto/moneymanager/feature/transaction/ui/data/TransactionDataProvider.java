package hhz.ktoeto.moneymanager.feature.transaction.ui.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.DateService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionUpdatedEvent;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

public class TransactionDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;

    private int maxSize;

    private static final int UNLIMITED = Integer.MAX_VALUE;

    @Getter
    protected transient TransactionFilter currentFilter;

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.refreshAll();
    }

    public TransactionDataProvider(TransactionService transactionService,
                                   UserContextHolder userContextHolder,
                                   DateService dateService,
                                   int maxSize) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;

        this.currentFilter = new TransactionFilter();
        currentFilter.setFromDate(dateService.currentMonthStart());
        currentFilter.setToDate(dateService.currentMonthEnd());

        this.maxSize = maxSize;
    }

    public void setFilter(TransactionFilter filter) {
        this.currentFilter = filter != null ? filter : new TransactionFilter();
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
