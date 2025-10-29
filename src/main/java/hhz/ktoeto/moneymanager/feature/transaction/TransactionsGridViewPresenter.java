package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.*;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.mixin.CanFormatAmount;
import hhz.ktoeto.moneymanager.ui.mixin.HasFilter;
import hhz.ktoeto.moneymanager.ui.mixin.HasTransactionsProvider;

public interface TransactionsGridViewPresenter extends ViewPresenter<TransactionsGridView>, HasFilter<TransactionFilter>,
        HasTransactionsProvider, CanFormatAmount, CanEdit<Transaction> {
}
