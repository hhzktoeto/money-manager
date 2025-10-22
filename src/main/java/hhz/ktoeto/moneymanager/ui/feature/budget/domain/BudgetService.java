package hhz.ktoeto.moneymanager.ui.feature.budget.domain;

import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final TransactionService transactionService;
    private final BudgetRepository repository;

    @Transactional
    public List<Budget> getAll(long userId, BudgetFilter filter) {
        log.debug("Fetching active budgets for user with id {}", userId);
        BudgetSpecification specification = BudgetSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        List<Budget> budgets = repository.findAll(specification);
        budgets.forEach(budget -> {
            TransactionFilter transactionFilter = new TransactionFilter();
            transactionFilter.setFromDate(budget.getStartDate());
            transactionFilter.setToDate(budget.getEndDate());
            transactionFilter.setCategoriesIds(
                    budget.getCategories().stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet())
            );
            transactionFilter.setType(
                    Budget.Type.EXPENSE == budget.getType()
                            ? Transaction.Type.EXPENSE
                            : Transaction.Type.INCOME
            );
            List<Transaction> transactions = transactionService.getAll(userId, transactionFilter);
            BigDecimal currentAmount = transactionService.calculateTotal(transactions);

            budget.setCurrentAmount(currentAmount);
        });

        return repository.findAll(specification);
    }

    @Transactional
    public Budget create(Budget budget) {
        if (budget.isRenewable()) {
            budget.calculateActiveDates();
        }
        log.debug("Creating budget for user with id {}. Budget: {}", budget.getUserId(), budget);
        return repository.save(budget);
    }

    @Transactional
    public Budget update(Budget updated, long userId) {
        log.debug("Updating budget for user with id {}. Budget: {}", updated.getUserId(), updated);
        Budget budget = getBudgetFromRepository(updated.getId());
        if (budget.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget update, which owner is user with id %d".formatted(userId, budget.getUserId()));
        }
        if (updated.isRenewable()) {
            updated.calculateActiveDates();
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        Budget budget = getBudgetFromRepository(id);
        if (budget.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget deletion, which owner is user with id %d".formatted(userId, budget.getUserId()));
        }

        repository.delete(budget);
    }

    public int count(long userId, BudgetFilter filter) {
        BudgetSpecification specification = BudgetSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();
        long count = repository.count(specification);
        return (int) Math.min(Integer.MAX_VALUE, count);
    }

    private Budget getBudgetFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Budget with id %d".formatted(id)));
    }
}
