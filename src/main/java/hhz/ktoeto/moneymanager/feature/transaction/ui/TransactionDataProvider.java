package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
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

import java.util.List;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class TransactionDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;
    @Getter
    protected transient TransactionFilter currentFilter;

    public TransactionDataProvider(TransactionService transactionService, UserContextHolder userContextHolder, DateService dateService) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;

        this.currentFilter = new TransactionFilter();
        currentFilter.setFromDate(dateService.currentMonthStart());
        currentFilter.setToDate(dateService.currentMonthEnd());
    }

    public void setCurrentFilter(TransactionFilter filter) {
        this.currentFilter = filter != null ? filter : new TransactionFilter();
        this.refreshAll();
    }

    public List<Transaction> getTransactions() {
        return this.fetchFromBackEnd(new Query<>(currentFilter)).toList();
    }

    @Override
    protected Stream<Transaction> fetchFromBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);

        Sort sort;
        if (query.getSortOrders().isEmpty()) {
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
        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        return transactionService.getPage(userId, filter, pageRequest).getContent().stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);

        return transactionService.count(userId, filter);
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
