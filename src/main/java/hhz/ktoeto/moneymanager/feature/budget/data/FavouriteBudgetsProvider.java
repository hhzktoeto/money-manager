package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class FavouriteBudgetsProvider extends BudgetsDataProvider {

    protected FavouriteBudgetsProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        super(budgetService, userContextHolder);
    }

    @Override
    protected Stream<Budget> fetchBudgets(long userId) {
        BudgetFilter filter = BudgetFilter.favouriteBudgetsFilter();
        Sort sort = Sort.by(Sort.Direction.ASC, "goalAmount");

        return this.getBudgetService().getAll(userId, filter, sort).stream();
    }
}
