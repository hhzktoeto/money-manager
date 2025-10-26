package hhz.ktoeto.moneymanager.feature.transaction.presenter;

import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionsGridPresenter implements TransactionsGridViewPresenter {

    private final TransactionDataProvider dataProvider;
    private final TransactionFormViewPresenter formPresenter;

    private TransactionsGridView view;

    @Override
    public void setView(TransactionsGridView view) {
        this.view = view;
    }

    @Override
    public TransactionFilter getFilter() {
        return dataProvider.getCurrentFilter();
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        dataProvider.setCurrentFilter(filter);
    }

    @Override
    public void onEditRequested(Transaction transaction) {
        formPresenter.openEditForm(transaction);
    }

    @Override
    public TransactionDataProvider getDataProvider() {
        return this.dataProvider;
    }
}
