package hhz.ktoeto.moneymanager.feature.transaction;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.HasCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.HasFilter;
import hhz.ktoeto.moneymanager.ui.HasTransactionsProvider;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;

public interface TransactionsGridViewPresenter extends ViewPresenter<TransactionsGridView>, HasFilter<TransactionFilter>,
        HasTransactionsProvider, HasCategoriesProvider {

    void onEditRequested(Transaction transaction);

    ListDataProvider<Category> getCategoriesProvider();
}
