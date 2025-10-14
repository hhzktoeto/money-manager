package hhz.ktoeto.moneymanager.feature.transaction.ui.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@VaadinSessionScope
@SpringComponent("recentTransactionsProvider")
public class RecentTransactionsDataProvider extends TransactionDataProvider {

    public RecentTransactionsDataProvider(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        int safeCount = (int) Math.min(Integer.MAX_VALUE, transactionService.count(SecurityUtils.getCurrentUserId(), this.getCurrentFilter()));
        return Math.min(safeCount, 5);
    }
}
