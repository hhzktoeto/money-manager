package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.*;
import hhz.ktoeto.moneymanager.ui.mixin.*;

public interface TransactionsGridViewPresenter extends ViewPresenter, HasFilter<TransactionFilter>,
        HasTransactionsProvider, HasCategoriesProvider, CanFormatAmount, CanEdit<Transaction> {

    TransactionsGridView getView();
}
