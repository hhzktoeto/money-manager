package hhz.ktoeto.moneymanager.transaction.model.budgetgoal;

import java.math.BigDecimal;

public record BudgetGoalDTO(
        long id,
        String type,
        String category,
        BigDecimal goalAmount
) {
}
