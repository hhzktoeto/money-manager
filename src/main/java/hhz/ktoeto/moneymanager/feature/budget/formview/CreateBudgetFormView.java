package hhz.ktoeto.moneymanager.feature.budget.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;

public class CreateBudgetFormView extends BudgetFormView {

    public CreateBudgetFormView(CreateBudgetFormPresenter presenter, SimpleAllCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
