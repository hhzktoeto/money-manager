package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.transaction.data.RecentTransactionsProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class RecentTransactionsGridPresenter extends TransactionsGridPresenter {


    public RecentTransactionsGridPresenter(UserContextHolder userContextHolder, FormattingService formattingService,
                                           TransactionService transactionService, RecentTransactionsProvider dataProvider,
                                           SimpleCategoriesProvider categoryDataProvider, ApplicationEventPublisher eventPublisher) {
        super(userContextHolder, formattingService, transactionService, dataProvider, categoryDataProvider, eventPublisher);
    }

    @Override
    public void initialize() {
        this.setView(new RecentTransactionsGridView(this));
    }
}
