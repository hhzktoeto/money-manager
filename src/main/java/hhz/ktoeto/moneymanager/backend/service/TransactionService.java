package hhz.ktoeto.moneymanager.backend.service;

import hhz.ktoeto.moneymanager.backend.dto.TransactionFilter;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.backend.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.backend.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.backend.repository.TransactionsRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository repository;

    public Page<Transaction> getPage(long userId, TransactionFilter filter, Pageable pageable) {
        log.debug("Fetching transactions for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(specification(userId, filter), pageable);
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        log.debug("Creating transaction for user with id {}. Transaction: {}", transaction.getUserId(), transaction);
        return repository.save(transaction);
    }

    @Transactional
    public Transaction update(Transaction updated, long userId) {
        log.debug("Updating transaction for user with id {}. Transaction: {}", updated.getUserId(), updated);
        Transaction transaction = getTransactionFromRepository(updated.getId());
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction update, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }

        return repository.save(updated);
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting transaction for user with id {}. Transaction ID: {}", userId, id);
        Transaction transaction = getTransactionFromRepository(id);
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction deletion, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }
        repository.delete(transaction);
    }

    public long count(long userId, TransactionFilter filter) {
        return repository.count(specification(userId, filter));
    }

    private Transaction getTransactionFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %d".formatted(id)));
    }

    private Specification<Transaction> specification(long userId, @NonNull TransactionFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);

            if (filter.getFromDate() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("date"), filter.getFromDate())
                );
            }
            if (filter.getToDate() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("date"), filter.getToDate())
                );
            }

            return predicate;
        };
    }
}
