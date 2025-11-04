package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Stream;

public abstract class BudgetsDataProvider extends AbstractBackEndDataProvider<Budget, BudgetFilter> {

    private final transient BudgetService budgetService;
    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;

    protected BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder, TransactionService transactionService) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
    }

    protected abstract BudgetFilter getFilter();

    protected abstract Sort getSort();

    public Stream<Budget> fetch() {
        long userId = this.userContextHolder.getCurrentUserId();
        BudgetFilter budgetFilter = this.getFilter();
        budgetFilter.setWithCategories(true);

        Sort budgetSort = this.getSort();

        List<Budget> budgets = this.budgetService.getAll(userId, budgetFilter, budgetSort);
        budgets.forEach(budget -> {
            List<Transaction> transactions = transactionService.getAllForBudget(budget);
            budget.setTransactions(transactions);
        });

        return budgets.stream();
    }

    @Override
    protected Stream<Budget> fetchFromBackEnd(Query<Budget, BudgetFilter> query) {
        // Unused
        return Stream.empty();
    }

    @Override
    protected int sizeInBackEnd(Query<Budget, BudgetFilter> query) {
        // Unused
        return 0;
    }

    @EventListener({
            BudgetCreatedEvent.class,
            BudgetUpdatedEvent.class,
            BudgetDeletedEvent.class,
            CategoryDeletedEvent.class,
            CategoryUpdatedEvent.class,
            TransactionCreatedEvent.class,
            TransactionUpdatedEvent.class,
            TransactionDeletedEvent.class
    })
    private void onAnyUpdate() {
        this.refreshAll();
    }
}
