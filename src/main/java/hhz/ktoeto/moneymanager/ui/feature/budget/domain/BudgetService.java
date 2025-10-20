package hhz.ktoeto.moneymanager.ui.feature.budget.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository repository;

    public List<Budget> getActiveBudgets(long userId) {
        log.debug("Fetching active budgets for user with id {}", userId);
        BudgetSpecification specification = BudgetSpecification.builder()
                .userId(userId)
                .filter(BudgetFilter.activeBudgetsFilter())
                .build();

        return repository.findAll(specification);
    }

    @Transactional
    public List<Budget> getAll() {
        return repository.findAll();
    }
}
