package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.backend.dto.TransactionFilter;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.backend.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@VaadinSessionScope
@RequiredArgsConstructor
@SpringComponent("allTransactionsProvider")
public class TransactionDataProvider extends AbstractBackEndDataProvider<Transaction, TransactionFilter> {

    protected final transient TransactionService transactionService;

    @Getter
    private transient TransactionFilter currentFilter = new TransactionFilter();

    public void setFilter(TransactionFilter filter) {
        this.currentFilter = filter != null ? filter : new TransactionFilter();
        this.refreshAll();
    }

    @Override
    protected Stream<Transaction> fetchFromBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = SecurityUtils.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);

        Sort sort;
        if (!query.getSortOrders().isEmpty()) {
            sort = query.getSortOrders().stream()
                    .map(order -> Sort.by(order.getDirection() == SortDirection.DESCENDING
                                    ? Sort.Direction.DESC
                                    : Sort.Direction.ASC,
                            order.getSorted())
                    )
                    .reduce(Sort.unsorted(), Sort::and);
        } else {
            sort = Sort.by(Sort.Direction.DESC, "date").and(Sort.by(Sort.Direction.DESC, "createdAt"));
        }

        int page = query.getOffset() / query.getLimit();
        int limit = query.getLimit();
        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        return transactionService.getPage(userId, filter, pageRequest).getContent().stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        long userId = SecurityUtils.getCurrentUserId();
        TransactionFilter filter = query.getFilter().orElse(currentFilter);

        return (int) Math.min(Integer.MAX_VALUE, transactionService.count(userId, filter));
    }

    @EventListener({
            TransactionCreatedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class
    })
    private void onTransactionCreatedDeleted() {
        this.refreshAll();
    }
}
