package hhz.ktoeto.moneymanager.feature.budget.domain;

import lombok.Data;

@Data
public class BudgetFilter {

    private Boolean isActive;
    private Boolean isRenewable;
    private Boolean isFavourite;

    public static BudgetFilter expiredRenewableBudgetsFilter() {
        BudgetFilter filter = new BudgetFilter();
        filter.setIsActive(false);
        filter.setIsRenewable(true);

        return filter;
    }

    public static BudgetFilter activeBudgetsFilter() {
        BudgetFilter filter = new BudgetFilter();
        filter.setIsActive(true);

        return filter;
    }

    public static BudgetFilter expiredBudgetsFilter() {
        BudgetFilter filter = new BudgetFilter();
        filter.setIsActive(false);

        return filter;
    }

    public static BudgetFilter favouriteBudgetsFilter() {
        BudgetFilter filter = new BudgetFilter();
        filter.setIsFavourite(true);

        return filter;
    }
}
