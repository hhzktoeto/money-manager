package hhz.ktoeto.moneymanager.ui.feature.budget.domain;

import lombok.Data;

@Data
public class BudgetFilter {

    private boolean isActive;

    public static BudgetFilter activeBudgetsFilter() {
        BudgetFilter filter = new BudgetFilter();
        filter.setActive(true);

        return filter;
    }
}
