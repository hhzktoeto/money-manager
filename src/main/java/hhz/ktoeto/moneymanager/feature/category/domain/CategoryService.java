package hhz.ktoeto.moneymanager.feature.category.domain;

import hhz.ktoeto.moneymanager.core.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.CategoryDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.CategoryUpdatedEvent;
import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public List<Category> getAll(long userId) {
        log.debug("Fetching all categories for user with id {}", userId);
        CategorySpecification specification = CategorySpecification.builder()
                .userId(userId)
                .build();

        return repository.findAll(specification);
    }

    @Transactional
    public Category create(Category category) {
        log.debug("Creating category for user with id {}. Category: {}", category.getUserId(), category);

        Category saved = repository.save(category);
        eventPublisher.publishEvent(new CategoryCreatedEvent(this, saved));

        return saved;
    }

    @Transactional
    public Category update(Category updated, long userId) {
        log.debug("Updating category for user with id {}. Category: {}", updated.getUserId(), updated);
        Category category = getCategoryFromRepository(updated.getId());
        if (category.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category updating, which owner is user with id %d".formatted(updated.getUserId(), userId));
        }

        Category saved = repository.save(updated);
        eventPublisher.publishEvent(new CategoryUpdatedEvent(this, saved));

        return saved;
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting category for user with id {}. Category ID: {}", userId, id);
        Category category = getCategoryFromRepository(id);
        if (category.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category deletion, which owner is user with id %d".formatted(userId, category.getUserId()));
        }
        repository.delete(category);
        eventPublisher.publishEvent(new CategoryDeletedEvent(this, category));
    }

    public boolean exist(long userId, CategoryFilter filter) {
        log.debug("Checking if category with filter {} exists for user with id {}", filter, userId);
        CategorySpecification specification = CategorySpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        return repository.exists(specification);
    }

    private Category getCategoryFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %s".formatted(id)));
    }
}
