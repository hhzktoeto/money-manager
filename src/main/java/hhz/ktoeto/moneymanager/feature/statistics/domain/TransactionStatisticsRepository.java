package hhz.ktoeto.moneymanager.feature.statistics.domain;

import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionStatisticsRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT DISTINCT
                EXTRACT(YEAR FROM t.date) AS year,
                EXTRACT(MONTH FROM t.date) AS month
            FROM Transaction t
            WHERE t.userId = :userId
            ORDER BY 1 ASC, 2 ASC
            """)
    List<YearMonthProjection> findAllTransactionsYearMonths(long userId);

    @Query("""
            SELECT new hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum(
                c.name,
                SUM(t.amount)
            )
            FROM Transaction t JOIN t.category c
            WHERE t.userId = :userId
            AND t.type = hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction.Type.EXPENSE
            AND t.date BETWEEN :from AND :to
            GROUP BY c.name
            HAVING SUM(t.amount) > 0
            ORDER BY SUM(t.amount) DESC
            """)
    List<CategorySum> findExpensesSumByCategory(long userId, LocalDate from, LocalDate to);

    @Query("""
            SELECT new hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum(
                c.name,
                SUM(t.amount)
            )
            FROM Transaction t JOIN t.category c
            WHERE t.userId = :userId
            AND t.type = hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction.Type.INCOME
            AND t.date BETWEEN :from AND :to
            GROUP BY c.name
            HAVING SUM(t.amount) > 0
            ORDER BY SUM(t.amount) DESC
            """)
    List<CategorySum> findIncomesSumByCategory(long userId, LocalDate from, LocalDate to);

    interface YearMonthProjection {
        int getYear();
        int getMonth();
    }
}
