package hhz.ktoeto.moneymanager.feature.transaction;

import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.presenter.TransactionsGridPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.view.TransactionsGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsGridConfig {

    @Bean
    @UIScope
    TransactionsGridViewPresenter recentTransactionsPresenter(UserContextHolder userContextHolder,
                                                              FormattingService formattingService,
                                                              TransactionService transactionService,
                                                              TransactionDataProvider recentTransactionsProvider,
                                                              TransactionFormViewPresenter formViewPresenter) {
        return new TransactionsGridPresenter(userContextHolder, formattingService, transactionService, recentTransactionsProvider, formViewPresenter);
    }

    @Bean
    @UIScope
    TransactionsGridViewPresenter allTransactionsPresenter(UserContextHolder userContextHolder,
                                                           FormattingService formattingService,
                                                           TransactionService transactionService,
                                                           TransactionDataProvider allTransactionsProvider,
                                                           TransactionFormViewPresenter formViewPresenter) {
        return new TransactionsGridPresenter(userContextHolder, formattingService, transactionService, allTransactionsProvider, formViewPresenter);
    }

    @Bean
    @UIScope
    public TransactionsGridView recentTransactionsGrid(TransactionsGridViewPresenter recentTransactionsPresenter) {
        return new TransactionsGrid(recentTransactionsPresenter, TransactionsGrid.Mode.RECENT);
    }

    @Bean
    @UIScope
    public TransactionsGridView allTransactionsGrid(TransactionsGridViewPresenter allTransactionsPresenter) {
        return new TransactionsGrid(allTransactionsPresenter, TransactionsGrid.Mode.ALL);
    }
}
