package hhz.ktoeto.moneymanager.feature.transaction.presenter;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionsGridPresenter implements TransactionsGridViewPresenter {

    private final TransactionDataProvider transactionsDataProvider;
    private final CategoryDataProvider categoryDataProvider;
    private final TransactionFormViewPresenter formPresenter;

    private TransactionsGridView view;

    @Override
    public void setView(TransactionsGridView view) {
        this.view = view;
    }

    @Override
    public TransactionFilter getFilter() {
        return transactionsDataProvider.getCurrentFilter();
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        transactionsDataProvider.setCurrentFilter(filter);
    }

    @Override
    public void onEditRequested(Transaction transaction) {
        formPresenter.openEditForm(transaction);
    }

    @Override
    public ListDataProvider<Category> getCategoriesProvider() {
        return this.categoryDataProvider;
    }

    @Override
    public TransactionDataProvider getTransactionsProvider() {
        return this.transactionsDataProvider;
    }
}
