package hhz.ktoeto.moneymanager.ui.mixin;

import com.vaadin.flow.data.provider.DataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;

import java.io.Serializable;

@FunctionalInterface
public interface HasTransactionsProvider extends Serializable {

    DataProvider<Transaction, TransactionFilter> getTransactionsProvider();
}
