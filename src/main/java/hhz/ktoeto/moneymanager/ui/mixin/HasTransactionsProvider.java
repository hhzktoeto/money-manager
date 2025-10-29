package hhz.ktoeto.moneymanager.ui.mixin;

import com.vaadin.flow.data.provider.DataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;

@FunctionalInterface
public interface HasTransactionsProvider {

    DataProvider<Transaction, TransactionFilter> getTransactionsProvider();
}
