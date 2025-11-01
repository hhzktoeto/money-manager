package hhz.ktoeto.moneymanager.feature.budget.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class ExpiredBudgetsProvider extends BudgetsDataProvider {

    private final TransactionService transactionService;

    protected ExpiredBudgetsProvider(BudgetService budgetService, UserContextHolder userContextHolder, TransactionService transactionService) {
        super(budgetService, userContextHolder);
        this.transactionService = transactionService;
    }

    @Override
    public Stream<Budget> fetchBudgets(long userId) {
        BudgetFilter budgetFilter = BudgetFilter.expiredBudgetsFilter();
        budgetFilter.setWithCategories(true);

        Sort budgetSort = Sort.by(Sort.Direction.ASC, "endDate");

        List<Budget> budgets = this.getBudgetService().getAll(userId, budgetFilter, budgetSort);
        budgets.forEach(budget -> {
            TransactionFilter transactionFilter = new TransactionFilter();
            transactionFilter.setCategoriesIds(budget.getCategories()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet())
            );
            transactionFilter.setType(Budget.Type.EXPENSE == budget.getType()
                    ? Transaction.Type.EXPENSE
                    : Transaction.Type.INCOME
            );
            transactionFilter.setFromDate(budget.getStartDate());
            transactionFilter.setToDate(budget.getEndDate());
            List<Transaction> transactions = transactionService.getAll(userId, transactionFilter);
            budget.setTransactions(transactions);
        });

        return budgets.stream();
    }
}
