package hhz.ktoeto.moneymanager.transaction.service;

import hhz.ktoeto.moneymanager.transaction.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.transaction.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.transaction.mapper.BudgetGoalMapper;
import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoal;
import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoalDTO;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.category.CategoryDTO;
import hhz.ktoeto.moneymanager.transaction.repository.BudgetGoalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetGoalService {

    private final BudgetGoalMapper mapper;
    private final CategoryService categoryService;
    private final BudgetGoalRepository repository;

    public List<BudgetGoal> getAll(long userId) {
        log.debug("Fetching all budget goals");
        return repository.findAllByUserId(userId);
    }

    @Transactional
    public BudgetGoal create(BudgetGoalDTO dto, long userId) {
        log.debug("Creating budget goal");
        BudgetGoal goal = mapper.toEntity(dto);
        Category category = categoryService.getByNameAndUserId(dto.category(), userId)
                .orElseGet(() -> categoryService.create(new CategoryDTO(dto.category()), userId));

        goal.setUserId(userId);
        goal.setCategory(category);

        return repository.save(goal);
    }

    @Transactional
    public BudgetGoal update(BudgetGoal dto, long userId) {
        log.debug("Updating budget goal");
        BudgetGoal goal = getGoalFromRepository(dto.getId());

        if (!goal.getCategory().getName().equals(dto.getCategory().getName())) {
            Category category = categoryService.getByNameAndUserId(dto.getCategory().getName(), userId)
                    .orElseGet(() -> categoryService.create(new CategoryDTO(dto.getCategory().getName()), userId));
            goal.setCategory(category);
        }

        return repository.save(dto);
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting budget goal");
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
