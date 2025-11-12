package hhz.ktoeto.moneymanager.feature.statistics.domain;

import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionStatisticsRepository transactionRepository;

    public List<YearMonth> getTransactionsYearMonths(long userId) {
        return transactionRepository.findAllTransactionsYearMonths(userId)
                .stream()
                .map(projection -> YearMonth.of(projection.getYear(), projection.getMonth()))
                .toList();
    }

    public Statistics getMonthCategoryStatistics(long userId, YearMonth yearMonth) {
        LocalDate fromDate = yearMonth.atDay(1);
        LocalDate toDate = yearMonth.atEndOfMonth();

        List<CategorySum> expenses = transactionRepository.findExpensesSumByCategory(userId, fromDate, toDate);
        List<CategorySum> incomes = transactionRepository.findIncomesSumByCategory(userId, fromDate, toDate);

        return Statistics.builder()
                .expensesCategoryAmounts(expenses)
                .incomesCategoryAmounts(incomes)
                .build();
    }
}
