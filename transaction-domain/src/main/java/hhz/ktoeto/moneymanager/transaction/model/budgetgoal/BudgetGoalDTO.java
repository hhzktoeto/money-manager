package hhz.ktoeto.moneymanager.transaction.model.budgetgoal;

import java.math.BigDecimal;

public record BudgetGoalDTO(
        String type,
        String category,
        BigDecimal goalAmount
) {
}
