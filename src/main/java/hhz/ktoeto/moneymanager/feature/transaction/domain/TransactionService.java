package hhz.ktoeto.moneymanager.feature.transaction.domain;

import hhz.ktoeto.moneymanager.core.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.core.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.core.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.core.event.TransactionUpdatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public List<Transaction> getAll(long userId, TransactionFilter filter) {
        TransactionSpecification specification = TransactionSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        return repository.findAll(specification);
    }

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
        Transaction saved = repository.save(transaction);
        eventPublisher.publishEvent(new TransactionCreatedEvent(this, saved));

        return saved;
    }

    @Transactional
    public Transaction update(Transaction updated, long userId) {
        log.debug("Updating transaction for user with id {}. Transaction: {}", updated.getUserId(), updated);
        Transaction transaction = getTransactionFromRepository(updated.getId());
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction update, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }

        Transaction saved = repository.save(updated);
        eventPublisher.publishEvent(new TransactionUpdatedEvent(this, saved));

        return saved;
    }

    @Transactional
    public void delete(long id, long userId) {
        log.debug("Deleting transaction for user with id {}. Transaction ID: {}", userId, id);
        Transaction transaction = getTransactionFromRepository(id);
        if (transaction.getUserId() != userId) {
            throw new NonOwnerRequestException("User with id %d requested transaction deletion, which owner is user with id %d".formatted(userId, transaction.getUserId()));
        }
        repository.delete(transaction);
        eventPublisher.publishEvent(new TransactionDeletedEvent(this, transaction));
    }

    public int count(long userId) {
        return doCount(userId, null);
    }

    public int count(long userId, TransactionFilter filter) {
        return doCount(userId, filter);
    }

    public BigDecimal calculateTotal(Collection<Transaction> transactions) {
        return transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Transaction getTransactionFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %d".formatted(id)));
    }

    private int doCount(long userId, @Nullable TransactionFilter filter) {
        TransactionSpecification specification = TransactionSpecification.builder()
                .userId(userId)
                .filter(filter)
                .build();

        long count = repository.count(specification);
        return (int) Math.min(Integer.MAX_VALUE, count);
    }
}
