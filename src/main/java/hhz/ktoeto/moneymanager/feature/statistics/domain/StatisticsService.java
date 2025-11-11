package hhz.ktoeto.moneymanager.feature.statistics.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionStatisticsRepository transactionRepository;

    public Set<YearMonth> getTransactionsYearMonths(long userId) {
        return transactionRepository.findAllTransactionsYearMonths(userId)
                .stream()
                .map(projection -> YearMonth.of(projection.getYear(), projection.getMonth()))
                .collect(Collectors.toSet());
    }

    public Set<CategoryAmount> getExpensePieData(long userId, YearMonth yearMonth) {
        LocalDate fromDate = yearMonth.atDay(1);
        LocalDate toDate = yearMonth.atEndOfMonth();

        return transactionRepository.findExpensesSumByCategory(userId, fromDate, toDate);
    }
}
