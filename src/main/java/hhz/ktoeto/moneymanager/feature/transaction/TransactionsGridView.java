package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;

import java.util.List;

public interface TransactionsGridView extends View {

    void updateItems(List<Transaction> transactions);
}
