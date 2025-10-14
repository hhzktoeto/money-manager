package hhz.ktoeto.moneymanager.feature.transaction.ui.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.DateService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;

@VaadinSessionScope
@SpringComponent("recentTransactionsProvider")
public class RecentTransactionsDataProvider extends TransactionDataProvider {

    public RecentTransactionsDataProvider(TransactionService transactionService,
                                          UserContextHolder userContextHolder,
                                          DateService dataService) {
        super(transactionService, userContextHolder, dataService);
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        int safeCount = (int) Math.min(Integer.MAX_VALUE, transactionService.count(userContextHolder.getCurrentUserId(), this.currentFilter));
        return Math.min(safeCount, 5);
    }
}
