package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.ui.HasDataProvider;
import hhz.ktoeto.moneymanager.ui.HasFilter;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;

public interface TransactionsGridViewPresenter extends ViewPresenter<TransactionsGridView>, HasFilter<TransactionFilter>, HasDataProvider<TransactionDataProvider> {

    void onEditRequested(Transaction transaction);
}
