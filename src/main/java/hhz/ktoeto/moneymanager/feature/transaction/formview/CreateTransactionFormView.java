package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;

public class CreateTransactionFormView extends TransactionFormView {

    protected CreateTransactionFormView(CreateTransactionFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
