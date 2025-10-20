package hhz.ktoeto.moneymanager.ui.feature.transaction.domain;

import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository repository;

    public Page<Transaction> getPage(long userId, TransactionFilter filter, Pageable pageable) {
        log.debug("Fetching transactions for user with id {}, page {}, size {}", userId, pageable.getPageNumber(), pageable.getPageSize());
        TransactionSpecification specification = TransactionSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        return repository.findAll(specification, pageable);
    }

    public TransactionsSummaries getSummaries(long userId, TransactionFilter filter) {
        log.debug("Calculating total transaction summaries for user with id {}. With filter: {}", userId, filter);
        TransactionSpecification specification = TransactionSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        List<Transaction> transactions = repository.findAll(specification);

        BigDecimal income = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TransactionsSummaries(income, expense, income.subtract(expense));
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
        TransactionSpecification specification = TransactionSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();
        long count = repository.count(specification);
        return (int) Math.min(Integer.MAX_VALUE, count);
    }

    private Transaction getTransactionFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %d".formatted(id)));
    }
}
