package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import org.springframework.context.event.EventListener;

import java.util.stream.Stream;

public abstract class BudgetsDataProvider extends AbstractBackEndDataProvider<Budget, BudgetFilter> {

    protected final transient BudgetService budgetService;
    private final transient UserContextHolder userContextHolder;

    protected BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
    }

    protected abstract Stream<Budget> fetchBudgets(long userId);

    public Stream<Budget> fetch() {
        return fetchBudgets(userContextHolder.getCurrentUserId());
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
            TransactionCreatedEvent.class,
            TransactionUpdatedEvent.class,
            TransactionDeletedEvent.class
    })
    private void onAnyUpdate() {
        this.refreshAll();
    }
}
