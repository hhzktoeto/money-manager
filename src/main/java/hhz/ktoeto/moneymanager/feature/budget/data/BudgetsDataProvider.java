package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class BudgetsDataProvider extends AbstractBackEndDataProvider<Budget, BudgetFilter> {

    private final transient BudgetService budgetService;
    private final transient UserContextHolder userContextHolder;

    private Sort customSort;

    public BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
    }

    public Stream<Budget> fetch(BudgetFilter filter) {
        return doFetch(filter, null);
    }

    public Stream<Budget> fetchWithSort(BudgetFilter filter, Sort sort) {
        return doFetch(filter, sort);
    }

    @Override
    protected Stream<Budget> fetchFromBackEnd(Query<Budget, BudgetFilter> query) {
        long userId = userContextHolder.getCurrentUserId();
        BudgetFilter filter = query.getFilter().orElse(null);

        Sort sort = Objects.requireNonNullElseGet(
                this.customSort,
                () -> Sort.by(Sort.Direction.DESC, "isFavourite")
                .and(Sort.by(Sort.Direction.ASC, "goalAmount"))
        );

        customSort = null;

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

    private Stream<Budget> doFetch(BudgetFilter filter, @Nullable Sort customSort) {
        Query<Budget, BudgetFilter> query = new Query<>(filter);
        if (customSort != null) {
            this.customSort = customSort;
        }

        return this.fetchFromBackEnd(query);
    }
}
