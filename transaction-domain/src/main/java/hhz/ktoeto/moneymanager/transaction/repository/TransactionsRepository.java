package hhz.ktoeto.moneymanager.transaction.repository;

import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(long userId);

    Page<Transaction> findByUserId(long userId, Pageable pageable);

    long countByUserId(long userId);
}
