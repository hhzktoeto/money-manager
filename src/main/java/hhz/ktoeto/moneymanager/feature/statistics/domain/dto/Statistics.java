package hhz.ktoeto.moneymanager.feature.statistics.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Statistics(
        List<CategoryAmount> expensesCategoryAmounts,
        List<CategoryAmount> incomesCategoryAmounts
) {
}
