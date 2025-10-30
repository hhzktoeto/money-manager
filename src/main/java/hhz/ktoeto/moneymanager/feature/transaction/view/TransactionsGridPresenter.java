package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.data.AbstractTransactionsDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.mixin.CanFormatAmount;
import hhz.ktoeto.moneymanager.ui.mixin.HasCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.mixin.HasTransactionsProvider;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

public abstract class TransactionsGridPresenter implements ViewPresenter, HasTransactionsProvider, HasCategoriesProvider,
        CanFormatAmount, CanEdit<Transaction> {

    protected final UserContextHolder userContextHolder;
    protected final FormattingService formattingService;
    protected final TransactionService transactionService;
    protected final AbstractTransactionsDataProvider dataProvider;
    protected final CategoryDataProvider categoryDataProvider;
    protected final ApplicationEventPublisher eventPublisher;

    @Getter
    protected TransactionsGridView view;

    public TransactionsGridPresenter(UserContextHolder userContextHolder,
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
    @PostConstruct
    public abstract void initialize();

    @Override
    public void onEditRequested(Transaction transaction) {
        this.eventPublisher.publishEvent(new TransactionEditRequested(this, transaction));
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
