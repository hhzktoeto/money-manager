package hhz.ktoeto.moneymanager.feature.transaction.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;

public class CreateTransactionFormView extends TransactionFormView {

    protected CreateTransactionFormView(CreateTransactionFormPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
