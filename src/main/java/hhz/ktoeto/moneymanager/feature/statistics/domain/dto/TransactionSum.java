package hhz.ktoeto.moneymanager.feature.statistics.domain.dto;

import java.math.BigDecimal;

public record TransactionSum(
        int year,
        int month,
        BigDecimal expensesSum,
        BigDecimal incomesSum
) {
}
