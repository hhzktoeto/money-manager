package hhz.ktoeto.moneymanager.ui.feature.transaction;

import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;

import java.util.List;

public interface TransactionsGridView extends View {

    void updateItems(List<Transaction> transactions);
}
