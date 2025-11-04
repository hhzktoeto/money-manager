package hhz.ktoeto.moneymanager.feature.category.domain;

import lombok.Data;

@Data
public class CategoryFilter {

    private String name;
    private boolean withTransactions;
    private boolean withBudgets;
    private boolean withGoals;
}
