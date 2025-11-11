package hhz.ktoeto.moneymanager.feature.statistics.domain;

import java.math.BigDecimal;

public record CategoryAmount(
        String categoryName,
        BigDecimal amount
) {
}
