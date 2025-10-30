package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class EditBudgetFormView extends BudgetFormView {

    public EditBudgetFormView(EditBudgetFormPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
