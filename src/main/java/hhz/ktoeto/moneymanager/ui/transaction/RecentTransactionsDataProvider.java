package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.backend.dto.TransactionFilter;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.backend.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@SpringComponent
@VaadinSessionScope
public class RecentTransactionsDataProvider extends TransactionDataProvider {

    private transient int quantity = 5;

    public RecentTransactionsDataProvider(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    protected int sizeInBackEnd(Query<Transaction, TransactionFilter> query) {
        int safeCount = (int) Math.min(Integer.MAX_VALUE, transactionService.count(SecurityUtils.getCurrentUserId(), currentFilter));
        return Math.min(safeCount, quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.refreshAll();
    }
}
