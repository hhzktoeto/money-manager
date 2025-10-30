package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;

import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class ExpiredBudgetsDataProvider extends BudgetsDataProvider {

    protected ExpiredBudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        super(budgetService, userContextHolder);
    }

    @Override
    protected Stream<Budget> fetchBudgets(long userId) {
        return this.budgetService.getExpiredBudgets(userId).stream();
    }
}
