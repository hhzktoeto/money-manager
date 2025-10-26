package hhz.ktoeto.moneymanager.feature.transaction.domain;

import java.math.BigDecimal;

public record TransactionsSummaries(
        BigDecimal incomes,
        BigDecimal expenses,
        BigDecimal total
) {
}
