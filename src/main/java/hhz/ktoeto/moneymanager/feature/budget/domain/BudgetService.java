package hhz.ktoeto.moneymanager.feature.budget.domain;

import hhz.ktoeto.moneymanager.core.event.BudgetCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.BudgetDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.BudgetUpdatedEvent;
import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<Budget> getAll(long userId, BudgetFilter filter, Sort sort) {
        log.debug("Fetching budgets for user with id {}. Filter - {}, Sort - {}", userId, filter, sort);
        BudgetSpecification specification = BudgetSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        return repository.findAll(specification, sort);
    }

    @Transactional
    public Budget create(Budget budget) {
        if (budget.isRenewable()) {
            budget.calculateActiveDates();
        }
        log.debug("Creating budget for user with id {}. Budget: {}", budget.getUserId(), budget);
        Budget created = repository.save(budget);
        eventPublisher.publishEvent(new BudgetCreatedEvent(this, created));

        return created;
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
        if (updated.getScope() == Budget.Scope.ALL) {
            updated.setCategories(Collections.emptySet());
        }

        Budget saved = repository.save(updated);
        eventPublisher.publishEvent(new BudgetUpdatedEvent(this, saved));

        return saved;
    }

    @Transactional
    public void delete(long id, long userId) {
        Budget budget = this.getBudgetFromRepository(id);
        if (budget.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget deletion, which owner is user with id %d".formatted(userId, budget.getUserId()));
        }

        repository.delete(budget);
        eventPublisher.publishEvent(new BudgetDeletedEvent(this, budget));
    }

    private Budget getBudgetFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Budget with id %d".formatted(id)));
    }
}
