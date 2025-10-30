package hhz.ktoeto.moneymanager.feature.transaction.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;

public class CreateTransactionForm extends AbstractTransactionFormView {

    protected CreateTransactionForm(CategoryDataProvider categoryProvider, TransactionFormViewPresenter presenter) {
        super(categoryProvider, presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
