package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.budget.BudgetFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class CreateBudgetForm extends AbstractBudgetFormView {

    public CreateBudgetForm(BudgetFormViewPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
