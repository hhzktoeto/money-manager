package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

public class TransactionsGridPresenter implements TransactionsGridViewPresenter, DataProviderListener<Transaction> {

    private final UserContextHolder userContextHolder;
    private final FormattingService formattingService;
    private final TransactionService transactionService;
    private final TransactionDataProvider dataProvider;
    private final CategoryDataProvider categoryDataProvider;
    private final ApplicationEventPublisher eventPublisher;

    private TransactionsGridView view;

    public TransactionsGridPresenter(UserContextHolder userContextHolder,
                                     FormattingService formattingService,
                                     TransactionService transactionService,
                                     TransactionDataProvider dataProvider,
                                     CategoryDataProvider categoryDataProvider,
                                     ApplicationEventPublisher eventPublisher) {
        this.userContextHolder = userContextHolder;
        this.formattingService = formattingService;
        this.transactionService = transactionService;
        this.dataProvider = dataProvider;
        this.categoryDataProvider = categoryDataProvider;
        this.eventPublisher = eventPublisher;

        this.dataProvider.addDataProviderListener(this);
    }

    @Override
    public void onDataChange(DataChangeEvent<Transaction> dataChangeEvent) {
        this.refresh();
    }

    @Override
    public void initialize(TransactionsGridView view) {
        this.view = view;
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
    public void onEditRequested(Transaction transaction) {
        eventPublisher.publishEvent(new TransactionEditRequested(this, transaction));
    }

    @Override
    public TransactionDataProvider getTransactionsProvider() {
        return this.dataProvider;
    }

    @Override
    public ListDataProvider<Category> getCategoriesProvider() {
        return this.categoryDataProvider;
    }

    @Override
    public String formatAmount(BigDecimal amount) {
        return this.formattingService.formatAmount(amount);
    }

    private void refresh() {
        TransactionsSummaries summaries = this.transactionService.getSummaries(this.userContextHolder.getCurrentUserId(), this.getFilter());
        this.view.updateSummaries(summaries);
    }
}
