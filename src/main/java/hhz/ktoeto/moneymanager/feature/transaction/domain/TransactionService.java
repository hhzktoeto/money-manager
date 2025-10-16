package hhz.ktoeto.moneymanager.feature.transaction.domain;

import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository repository;

    public List<Transaction> getFiltered(long userId, TransactionFilter filter) {
        log.debug("Fetching transactions for user with id {}. With filter: {}", userId, filter);
        return repository.findAll(specification(userId, filter));
    }

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

    public int count(long userId, TransactionFilter filter) {
        long count = repository.count(specification(userId, filter));
        return (int) Math.min(Integer.MAX_VALUE, count);
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
