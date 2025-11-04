package hhz.ktoeto.moneymanager.feature.budget.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;

public class EditBudgetFormView extends BudgetFormView {

    public EditBudgetFormView(EditBudgetFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
