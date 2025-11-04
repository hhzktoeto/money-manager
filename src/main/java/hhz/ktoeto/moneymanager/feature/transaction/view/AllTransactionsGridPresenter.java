package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.transaction.data.AllTransactionsProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.ui.mixin.HasFilter;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class AllTransactionsGridPresenter extends TransactionsGridPresenter implements DataProviderListener<Transaction>, HasFilter<TransactionFilter> {

    private HasUpdatableData<TransactionsSummaries> hasUpdatableDataDelegate;

    public AllTransactionsGridPresenter(UserContextHolder userContextHolder, FormattingService formattingService,
                                        TransactionService transactionService, AllTransactionsProvider dataProvider,
                                        SimpleCategoriesProvider categoryDataProvider, ApplicationEventPublisher eventPublisher) {
        super(userContextHolder, formattingService, transactionService, dataProvider, categoryDataProvider, eventPublisher);
    }

    @Override
    public void initialize() {
        AllTransactionsGridView view = new AllTransactionsGridView(this);
        this.setView(view);
        this.hasUpdatableDataDelegate = view;

        this.getDataProvider().addDataProviderListener(this);
        this.refresh();
    }

    @Override
    public TransactionFilter getFilter() {
        return this.getDataProvider().getCurrentFilter();
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        this.getDataProvider().setCurrentFilter(filter);
    }

    @Override
    public void onDataChange(DataChangeEvent<Transaction> dataChangeEvent) {
        this.refresh();
    }

    private void refresh() {
        TransactionsSummaries summaries = this.getTransactionService().getSummaries(this.getUserContextHolder().getCurrentUserId(), this.getFilter());
        this.hasUpdatableDataDelegate.update(summaries);
    }
}
