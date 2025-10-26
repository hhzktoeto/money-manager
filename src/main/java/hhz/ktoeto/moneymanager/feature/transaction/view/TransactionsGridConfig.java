package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.FormView;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.view.data.TransactionDataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsGridConfig {

    @Bean
    @UIScope
    TransactionsGridViewPresenter recentTransactionsPresenter(TransactionDataProvider recentTransactionsProvider,
                                                              FormViewPresenter<Transaction, FormView<Transaction>> formViewPresenter) {
        return new TransactionsGridPresenter(recentTransactionsProvider, formViewPresenter);
    }

    @Bean
    @UIScope
    TransactionsGridViewPresenter allTransactionsPresenter(TransactionDataProvider allTransactionsProvider,
                                                           FormViewPresenter<Transaction, FormView<Transaction>> formViewPresenter) {
        return new TransactionsGridPresenter(allTransactionsProvider, formViewPresenter);
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
