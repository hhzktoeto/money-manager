package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.budget.BudgetFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class EditBudgetForm extends AbstractBudgetFormView {

    public EditBudgetForm(BudgetFormViewPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
