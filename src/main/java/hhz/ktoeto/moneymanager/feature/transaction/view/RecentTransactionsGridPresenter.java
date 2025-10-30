package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.data.RecentTransactionsProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent("recentTransactionsPresenter")
public class RecentTransactionsGridPresenter extends TransactionsGridPresenter {


    public RecentTransactionsGridPresenter(UserContextHolder userContextHolder, FormattingService formattingService,
                                           TransactionService transactionService, RecentTransactionsProvider dataProvider,
                                           CategoryDataProvider categoryDataProvider, ApplicationEventPublisher eventPublisher) {
        super(userContextHolder, formattingService, transactionService, dataProvider, categoryDataProvider, eventPublisher);
    }

    @Override
    @PostConstruct
    public void initializeView() {
        this.view = new RecentTransactionsGrid(this);
    }

    @Override
    public TransactionFilter getFilter() {
        throw new UnsupportedOperationException("getFilter() should not be called on RecentTransactionsGridPresenter");
    }

    @Override
    public void setFilter(TransactionFilter filter) {
        throw new UnsupportedOperationException("setFilter() should not be called on RecentTransactionsGridPresenter");
    }
}
