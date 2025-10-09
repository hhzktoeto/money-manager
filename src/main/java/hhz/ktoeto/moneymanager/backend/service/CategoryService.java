package hhz.ktoeto.moneymanager.backend.service;

import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.backend.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> getAll(long userId) {
        log.debug("Fetching all categories for user with id {}", userId);
        return repository.findAllByUserId(userId);
    }

    @Transactional
    public Category create(Category category) {
        log.debug("Creating category for user with id {}. Category: {}", category.getUserId(), category);
        return repository.save(category);
    }

    @Transactional
    public Category update(Category updated, long userId) {
        log.debug("Updating category for user with id {}. Category: {}", updated.getUserId(), updated);
        Category category = getCategoryFromRepository(updated.getId());
        if (category.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category updating, which owner is user with id %d".formatted(updated.getUserId(), userId));
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting category for user with id {}. Category ID: {}", userId, id);
        Category category = getCategoryFromRepository(id);
        if (category.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category deletion, which owner is user with id %d".formatted(userId, category.getUserId()));
        }
        repository.delete(category);
    }

    public boolean exist(String name, long userId) {
        log.debug("Checking if category with name {} exists for user with id {}", name, userId);
        return repository.existsByNameAndUserId(name, userId);
    }

    private Category getCategoryFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %s".formatted(id)));
    }
}
