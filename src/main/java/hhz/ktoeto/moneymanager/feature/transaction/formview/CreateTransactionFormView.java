package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;

public class CreateTransactionFormView extends TransactionFormView {

    protected CreateTransactionFormView(CreateTransactionFormPresenter presenter, SimpleAllCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
