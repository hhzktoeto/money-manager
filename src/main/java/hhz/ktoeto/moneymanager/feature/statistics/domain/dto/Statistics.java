package hhz.ktoeto.moneymanager.feature.statistics.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Statistics(
        List<CategorySum> expensesCategoryAmounts,
        List<CategorySum> incomesCategoryAmounts
) {
}
