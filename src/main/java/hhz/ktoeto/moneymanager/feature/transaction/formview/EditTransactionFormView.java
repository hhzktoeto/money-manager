package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;

public class EditTransactionFormView extends TransactionFormView {

    protected EditTransactionFormView(EditTransactionFormPresenter presenter, SimpleAllCategoriesProvider categoryProvider) {
        super(presenter, categoryProvider);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
