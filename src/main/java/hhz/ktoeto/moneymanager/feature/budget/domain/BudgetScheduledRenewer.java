package hhz.ktoeto.moneymanager.feature.budget.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BudgetScheduledRenewer {

    private final BudgetRepository repository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void autoRenew() {
        log.info("Started budgets auto renewing task");
        BudgetSpecification specification = BudgetSpecification.builder()
                .filter(BudgetFilter.expiredRenewableBudgetsFilter())
                .build();

        List<Budget> expiredRenewableBudgets = repository.findAll(specification);
        if (expiredRenewableBudgets.isEmpty()) {
            log.info("No budgets to renew found, cancelling the task");
            return;
        }

        log.info("Found {} budgets to renew", expiredRenewableBudgets.size());
        for (Budget budget : expiredRenewableBudgets) {
            Budget newBudget = this.renewBudget(budget);
            if (budget.isFavourite()) {
                budget.setFavourite(false);
            }

            repository.save(newBudget);
        }
        log.info("All budgets renewed, task finished");
    }

    private Budget renewBudget(Budget budget) {
        Budget newBudget = new Budget();
        newBudget.setName(budget.getName());
        newBudget.setRenewable(budget.isRenewable());
        newBudget.setFavourite(budget.isFavourite());
        newBudget.setType(budget.getType());
        newBudget.setScope(budget.getScope());
        newBudget.setActivePeriod(budget.getActivePeriod());
        newBudget.setGoalAmount(budget.getGoalAmount());
        newBudget.calculateActiveDates();
        newBudget.setUserId(budget.getUserId());

        newBudget.getCategories().addAll(budget.getCategories());

        return newBudget;
    }
}
