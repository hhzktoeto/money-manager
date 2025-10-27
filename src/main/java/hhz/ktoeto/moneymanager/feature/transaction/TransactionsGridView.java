package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.ui.View;

public interface TransactionsGridView extends View {

    void updateSummaries(TransactionsSummaries summaries);
}
