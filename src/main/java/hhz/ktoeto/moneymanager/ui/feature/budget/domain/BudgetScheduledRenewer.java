package hhz.ktoeto.moneymanager.ui.feature.budget.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BudgetScheduledRenewer {

    private final BudgetRepository repository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    protected void autoRenew() {
        BudgetSpecification specification = BudgetSpecification.builder()
                .filter(BudgetFilter.expiredRenewableBudgetsFilter())
                .build();

        List<Budget> expiredRenewableBudgets = repository.findAll(specification);
        for (Budget budget : expiredRenewableBudgets) {
            Budget newBudget = this.renewBudget(budget);

            repository.save(newBudget);
        }
    }

    private Budget renewBudget(Budget budget) {
        Budget newBudget = new Budget();
        newBudget.setName(budget.getName());
        newBudget.setRenewable(budget.isRenewable());
        newBudget.setType(budget.getType());
        newBudget.setScope(budget.getScope());
        newBudget.setActivePeriod(budget.getActivePeriod());
        newBudget.setGoalAmount(budget.getGoalAmount());
        newBudget.calculateActiveDates();
        newBudget.setUserId(budget.getUserId());
        newBudget.setCategories(budget.getCategories());

        return newBudget;
    }
}
