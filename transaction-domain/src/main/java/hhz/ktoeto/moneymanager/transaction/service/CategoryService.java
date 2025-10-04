package hhz.ktoeto.moneymanager.transaction.service;

import hhz.ktoeto.moneymanager.transaction.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.transaction.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.transaction.mapper.CategoryMapper;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.category.CategoryDTO;
import hhz.ktoeto.moneymanager.transaction.repository.CategoryRepository;
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

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public List<Category> getAll(long userId) {
        log.debug("Fetching all categories for user with id {}", userId);
        return repository.findAllByUserId(userId);
    }

    public Page<Category> getPage(long userId, Pageable pageable) {
        log.debug("Fetching categories for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return repository.findByUserId(userId, pageable);
    }

    public Optional<Category> getByNameAndUserId(String name, long userId) {
        log.debug("Trying to find category by name {}", name);
        Optional<Category> category = repository.findByNameAndUserId(name, userId);
        log.info("Found category: {}", category);

        return category;
    }

    @Transactional
    public Category create(CategoryDTO dto, long userId) {
        log.debug("Creating new category: {}", dto.name());
        Category category = mapper.toEntity(dto);
        category.setUserId(userId);
        return repository.save(category);
    }

    @Transactional
    public Category update(Category updated, long userId) {
        log.debug("Updating category");
        if (updated.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category updating, which owner is user with id %d".formatted(updated.getUserId(), userId));
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        Category category = getCategoryFromRepository(id);
        if (category.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested category deletion, which owner is user with id %d".formatted(userId, category.getUserId()));
        }
        repository.delete(category);
    }

    public long count(long userId) {
        return repository.countAllByUserId(userId);
    }

    private Category getCategoryFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %s".formatted(id)));
    }
}
