package hhz.ktoeto.moneymanager.feature.statistics.domain;

import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionStatisticsRepository transactionRepository;

    public List<CategorySum> getCategorySums(long userId, LocalDate from, LocalDate to, Transaction.Type type) {
        LocalDate effectiveFrom = from == null ? LocalDate.ofYearDay(2024, 1) : from;
        LocalDate effectiveTo = to == null ? LocalDate.ofYearDay(2999, 1) : to;

        return transactionRepository.findCategoriesSums(userId, effectiveFrom, effectiveTo, type);
    }

    public List<TransactionSum> getTransactionSums(long userId) {
        return transactionRepository.findTransactionsSums(userId);
    }
}
