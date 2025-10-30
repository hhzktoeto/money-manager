package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.data.AllTransactionsProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent("allTransactionsPresenter")
public class AllTransactionsGridPresenter extends AbstractTransactionsGridViewPresenter implements DataProviderListener<Transaction> {

    public AllTransactionsGridPresenter(UserContextHolder userContextHolder, FormattingService formattingService,
                                        TransactionService transactionService, AllTransactionsProvider dataProvider,
                                        CategoryDataProvider categoryDataProvider, ApplicationEventPublisher eventPublisher) {
        super(userContextHolder, formattingService, transactionService, dataProvider, categoryDataProvider, eventPublisher);
    }

    @Override
    public void initialize(TransactionsGridView view) {
        this.view = view;

        this.dataProvider.addDataProviderListener(this);
        this.refresh();
    }

    @Override
    public TransactionFilter getFilter() {
        return this.dataProvider.getCurrentFilter();
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        this.dataProvider.setCurrentFilter(filter);
    }

    @Override
    public void onDataChange(DataChangeEvent<Transaction> dataChangeEvent) {
        this.refresh();
    }

    private void refresh() {
        TransactionsSummaries summaries = this.transactionService.getSummaries(this.userContextHolder.getCurrentUserId(), this.getFilter());
        this.view.updateSummaries(summaries);
    }
}
