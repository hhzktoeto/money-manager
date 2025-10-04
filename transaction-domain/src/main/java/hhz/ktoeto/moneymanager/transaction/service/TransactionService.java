package hhz.ktoeto.moneymanager.transaction.service;

import hhz.ktoeto.moneymanager.transaction.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.transaction.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.transaction.mapper.TransactionMapper;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.category.CategoryDTO;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.model.transaction.TransactionDTO;
import hhz.ktoeto.moneymanager.transaction.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper mapper;
    private final CategoryService categoryService;
    private final TransactionsRepository repository;

    public List<Transaction> getAll(long userId) {
        log.debug("Fetching all transactions for user with id {}", userId);
        return repository.findAllByUserId(userId);
    }

    public Page<Transaction> getPage(long userId, Pageable pageable) {
        log.debug("Fetching transactions for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return repository.findByUserId(userId, pageable);
    }

    @Transactional
    public Transaction create(TransactionDTO dto, long userId) {
        log.info("Processing Transaction create request");
        Transaction transaction = mapper.toEntity(dto);
        Category category = categoryService.getByNameAndUserId(dto.category(), userId)
                .orElseGet(() -> categoryService.create(new CategoryDTO(dto.category()), userId));

        transaction.setUserId(userId);
        transaction.setCategory(category);

        return repository.save(transaction);
    }

    @Transactional
    public Transaction update(Transaction updated, long userId) {
        Transaction transaction = getTransactionFromRepository(updated.getId());
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction update, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }

        if (!Objects.equals(transaction.getCategory().getName(), updated.getCategory().getName())) {
            Category category = categoryService.getByNameAndUserId(updated.getCategory().getName(), userId)
                    .orElseGet(() -> categoryService.create(new CategoryDTO(updated.getCategory().getName()), userId));
            updated.setCategory(category);
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        Transaction transaction = getTransactionFromRepository(id);
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction deletion, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }
        repository.delete(transaction);
    }

    public long count(long userId) {
        return repository.countByUserId(userId);
    }

    private Transaction getTransactionFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %d".formatted(id)));
    }
}
