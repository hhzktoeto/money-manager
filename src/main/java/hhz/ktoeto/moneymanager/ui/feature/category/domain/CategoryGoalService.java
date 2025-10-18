package hhz.ktoeto.moneymanager.ui.feature.category.domain;

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
public class CategoryGoalService {

    private final CategoryGoalRepository repository;

    public Page<CategoryGoal> getPage(long userId, Pageable pageable) {
        log.debug("Fetching budget goals for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return repository.findByUserId(userId, pageable);
    }

    @Transactional
    public CategoryGoal create(CategoryGoal goal) {
        log.debug("Creating budget goal for user with id {}. Budget goal: {}", goal.getUserId(), goal);
        return repository.save(goal);
    }

    @Transactional
    public CategoryGoal update(CategoryGoal updated, long userId) {
        log.debug("Updating budget goal for user with id {}. Budget goal: {}", updated.getUserId(), updated);
        CategoryGoal goal = getGoalFromRepository(updated.getId());
        if (goal.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget goal update, which owner is user with id %d".formatted(userId, goal.getUserId()));
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting budget goal for user with id {}. Budget goal ID: {}", userId, id);
        CategoryGoal goal = getGoalFromRepository(id);
        if (goal.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested budget goal deletion, which owner is user with id %d".formatted(userId, goal.getUserId()));
        }
        repository.delete(goal);
    }

    private CategoryGoal getGoalFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Budget goal with id %d".formatted(id)));
    }
}
