package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.*;

public interface TransactionsGridViewPresenter extends ViewPresenter<TransactionsGridView>, HasFilter<TransactionFilter>,
        HasTransactionsProvider, CanFormatAmount, CanEdit<Transaction> {
}
