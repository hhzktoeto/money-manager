package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import hhz.ktoeto.moneymanager.ui.FormView;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.view.data.TransactionDataProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionsGridPresenter implements TransactionsGridViewPresenter, DataProviderListener<Transaction> {

    private final TransactionDataProvider dataProvider;
    private final transient FormViewPresenter<Transaction, FormView<Transaction>> formPresenter;

    private transient TransactionsGridView view;

    @Override
    public void setView(TransactionsGridView view) {
        this.view = view;
    }

    @Override
    public TransactionFilter getFilter() {
        return dataProvider.getCurrentFilter();
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        dataProvider.setCurrentFilter(filter);
    }

    @Override
    public void onEditRequested(Transaction transaction) {
        formPresenter.openEditForm(transaction);
    }

    @Override
    public void onDataChange(DataChangeEvent<Transaction> dataChangeEvent) {
        this.refresh();
    }

    @PostConstruct
    private void init() {
        this.dataProvider.addDataProviderListener(this);
        this.refresh();
    }

    private void refresh() {
        UI.getCurrent().access(() -> {
            Query<Transaction, TransactionFilter> query = new Query<>(this.dataProvider.getCurrentFilter());
            List<Transaction> transactions = this.dataProvider.fetch(query).toList();
            this.view.updateItems(transactions);
        });
    }
}
