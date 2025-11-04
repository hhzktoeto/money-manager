package hhz.ktoeto.moneymanager.feature.budget.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;

public class EditBudgetFormView extends BudgetFormView {

    public EditBudgetFormView(EditBudgetFormPresenter presenter, SimpleAllCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
