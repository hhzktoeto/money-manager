package hhz.ktoeto.moneymanager.feature.budget.domain;

import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetGoalService {

    private final BudgetGoalRepository repository;

    public Page<BudgetGoal> getPage(long userId, Pageable pageable) {
        log.debug("Fetching budget goals for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return repository.findByUserId(userId, pageable);
    }

    @Transactional
    public BudgetGoal create(BudgetGoal goal) {
        log.debug("Creating budget goal for user with id {}. Budget goal: {}", goal.getUserId(), goal);
        return repository.save(goal);
    }

    @Transactional
    public BudgetGoal update(BudgetGoal updated, long userId) {
        log.debug("Updating budget goal for user with id {}. Budget goal: {}", updated.getUserId(), updated);
        BudgetGoal goal = getGoalFromRepository(updated.getId());
        if (goal.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget goal update, which owner is user with id %d".formatted(userId, goal.getUserId()));
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting budget goal for user with id {}. Budget goal ID: {}", userId, id);
        BudgetGoal goal = getGoalFromRepository(id);
        if (goal.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget goal deletion, which owner is user with id %d".formatted(userId, goal.getUserId()));
        }
        repository.delete(goal);
    }

    private BudgetGoal getGoalFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Budget goal with id %d".formatted(id)));
    }
}
