package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class CreateBudgetFormView extends BudgetFormView {

    public CreateBudgetFormView(CreateBudgetFormPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
