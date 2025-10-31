package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class RecentTransactionsProvider extends AbstractTransactionsDataProvider {

    public RecentTransactionsProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        super(userContextHolder, transactionService);
    }

    @Override
    protected Stream<Transaction> doFetch(long userId, Query<Transaction, TransactionFilter> query) {
        // This won't be used but must be called to avoid internal Vaadin's exception
        query.getLimit();
        query.getPage();
        query.getOffset();
        // This won't be used but must be called to avoid internal Vaadin's exception

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageRequest = PageRequest.of(0, 5, sort);

        return this.getTransactionService().getPage(userId, pageRequest).stream();
    }

    @Override
    protected int doCount(long userId, Query<Transaction, TransactionFilter> query) {
        int actualCount = this.getTransactionService().count(userId);
        return Math.min(actualCount, 5);
    }
}
