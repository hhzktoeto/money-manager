package hhz.ktoeto.moneymanager.ui.feature.budget.ui.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.BudgetCreatedEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.TransactionUpdatedEvent;
import org.springframework.context.event.EventListener;

import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class BudgetsDataProvider extends AbstractBackEndDataProvider<Budget, BudgetFilter> {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;

    public BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
    }

    @Override
    protected Stream<Budget> fetchFromBackEnd(Query<Budget, BudgetFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        BudgetFilter filter = query.getFilter().orElse(null);

        return budgetService.getAll(userId, filter).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Budget, BudgetFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        BudgetFilter filter = query.getFilter().orElse(null);

        return budgetService.count(userId, filter);
    }

    @EventListener({
            BudgetCreatedEvent.class,
            TransactionCreatedEvent.class,
            TransactionUpdatedEvent.class,
            TransactionDeletedEvent.class
    })
    private void onAnyUpdate() {
        this.refreshAll();
    }
}
