package hhz.ktoeto.moneymanager.feature.transaction.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class EditTransactionFormView extends TransactionFormView {

    protected EditTransactionFormView(EditTransactionFormPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
