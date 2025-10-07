package hhz.ktoeto.moneymanager.backend.transaction_domain.service;

import hhz.ktoeto.moneymanager.backend.transaction_domain.entity.Transaction;
import hhz.ktoeto.moneymanager.backend.transaction_domain.exception.EntityNotFoundException;
import hhz.ktoeto.moneymanager.backend.transaction_domain.exception.NonOwnerRequestException;
import hhz.ktoeto.moneymanager.backend.transaction_domain.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

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

    public long count(long userId) {
        return repository.countByUserId(userId);
    }

    private Transaction getTransactionFromRepository(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Transaction with id %d".formatted(id)));
    }
}
