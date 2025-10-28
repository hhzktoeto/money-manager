package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.core.event.BudgetCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.BudgetDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.BudgetUpdatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class BudgetsDataProvider extends AbstractBackEndDataProvider<Budget, BudgetFilter> {

    private final transient BudgetService budgetService;
    private final transient UserContextHolder userContextHolder;

    public BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
    }

    @Override
    protected Stream<Budget> fetchFromBackEnd(Query<Budget, BudgetFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        BudgetFilter filter = query.getFilter().orElse(null);
        Sort sort = Sort.by(Sort.Direction.DESC, "isFavourite")
                .and(Sort.by(Sort.Direction.ASC, "endDate"));


        return budgetService.getAll(userId, filter, sort).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Budget, BudgetFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        BudgetFilter filter = query.getFilter().orElse(null);

        return budgetService.count(userId, filter);
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
