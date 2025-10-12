package hhz.ktoeto.moneymanager.backend.repository;

import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

}
