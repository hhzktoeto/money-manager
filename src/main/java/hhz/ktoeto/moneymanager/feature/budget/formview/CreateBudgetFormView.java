package hhz.ktoeto.moneymanager.feature.budget.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;

public class CreateBudgetFormView extends BudgetFormView {

    public CreateBudgetFormView(CreateBudgetFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
