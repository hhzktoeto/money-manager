package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.data.domain.Sort;

@SpringComponent
@VaadinSessionScope
public class FavouriteBudgetsProvider extends BudgetsDataProvider {

    protected FavouriteBudgetsProvider(BudgetService budgetService, UserContextHolder userContextHolder, TransactionService transactionService) {
        super(budgetService, userContextHolder, transactionService);
    }

    @Override
    protected BudgetFilter getFilter() {
        return BudgetFilter.favouriteBudgetsFilter();
    }

    @Override
    protected Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "goalAmount");
    }
}
