package hhz.ktoeto.moneymanager.ui.feature.transaction;

import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;

public interface TransactionsGridViewPresenter extends ViewPresenter<TransactionsGridView> {

    TransactionFilter getCurrentFilter();

    void setCurrentFilter(TransactionFilter filter);

    void onEditRequested(Transaction transaction);
}
