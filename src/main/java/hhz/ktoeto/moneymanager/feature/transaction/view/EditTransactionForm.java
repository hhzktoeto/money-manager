package hhz.ktoeto.moneymanager.feature.transaction.view;

import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;

public class EditTransactionForm extends AbstractTransactionFormView {

    protected EditTransactionForm(CategoryDataProvider categoryProvider, TransactionFormViewPresenter presenter) {
        super(categoryProvider, presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
