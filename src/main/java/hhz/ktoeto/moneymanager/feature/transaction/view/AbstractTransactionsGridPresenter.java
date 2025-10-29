package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.data.AbstractTransactionsDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

public abstract class AbstractTransactionsGridPresenter implements TransactionsGridViewPresenter {

    protected final UserContextHolder userContextHolder;
    protected final FormattingService formattingService;
    protected final TransactionService transactionService;
    protected final AbstractTransactionsDataProvider dataProvider;
    protected final CategoryDataProvider categoryDataProvider;
    protected final ApplicationEventPublisher eventPublisher;

    protected TransactionsGridView view;

    public AbstractTransactionsGridPresenter(UserContextHolder userContextHolder,
                                             FormattingService formattingService,
                                             TransactionService transactionService,
                                             AbstractTransactionsDataProvider dataProvider,
                                             CategoryDataProvider categoryDataProvider,
                                             ApplicationEventPublisher eventPublisher) {
        this.userContextHolder = userContextHolder;
        this.formattingService = formattingService;
        this.transactionService = transactionService;
        this.dataProvider = dataProvider;
        this.categoryDataProvider = categoryDataProvider;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onEditRequested(Transaction transaction) {
        eventPublisher.publishEvent(new TransactionEditRequested(this, transaction));
    }

    @Override
    public AbstractTransactionsDataProvider getTransactionsProvider() {
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
}
