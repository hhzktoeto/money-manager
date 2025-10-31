package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class AllTransactionsProvider extends AbstractTransactionsDataProvider {

    public AllTransactionsProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        super(userContextHolder, transactionService);
    }

    @Override
    protected Stream<Transaction> doFetch(long userId, Query<Transaction, TransactionFilter> query) {
        TransactionFilter filter = query.getFilter().orElse(this.getCurrentFilter());
        Sort sort = query.getSortOrders().stream()
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

        int limit = query.getLimit();
        int page = query.getOffset() / query.getLimit();

        Pageable pageRequest = PageRequest.of(page, limit, sort);

        return this.getTransactionService().getPage(userId, filter, pageRequest).stream();
    }

    @Override
    protected int doCount(long userId, Query<Transaction, TransactionFilter> query) {
        TransactionFilter filter = query.getFilter().orElse(this.getCurrentFilter());
        return getTransactionService().count(userId, filter);
    }
}
