package hhz.ktoeto.moneymanager.ui.feature.transaction.domain;

import java.math.BigDecimal;

public record TransactionsSummaries(
        BigDecimal incomes,
        BigDecimal expenses,
        BigDecimal total
) {
}
