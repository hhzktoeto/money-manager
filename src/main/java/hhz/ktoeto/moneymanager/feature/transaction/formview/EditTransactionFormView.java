package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;

public class EditTransactionFormView extends TransactionFormView {

    protected EditTransactionFormView(EditTransactionFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
