package hhz.ktoeto.moneymanager.feature.transaction;

import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.presenter.TransactionsGridPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.view.TransactionsGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsGridConfig {

    @Bean
    @UIScope
    TransactionsGridViewPresenter recentTransactionsPresenter(TransactionDataProvider recentTransactionsProvider,
                                                              CategoryDataProvider categoryDataProvider,
                                                              TransactionFormViewPresenter formViewPresenter) {
        return new TransactionsGridPresenter(recentTransactionsProvider, categoryDataProvider, formViewPresenter);
    }

    @Bean
    @UIScope
    TransactionsGridViewPresenter allTransactionsPresenter(TransactionDataProvider allTransactionsProvider,
                                                           CategoryDataProvider categoryDataProvider,
                                                           TransactionFormViewPresenter formViewPresenter) {
        return new TransactionsGridPresenter(allTransactionsProvider, categoryDataProvider, formViewPresenter);
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
