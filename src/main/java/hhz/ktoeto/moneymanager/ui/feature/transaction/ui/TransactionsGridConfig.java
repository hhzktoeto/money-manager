package hhz.ktoeto.moneymanager.ui.feature.transaction.ui;

import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.data.TransactionDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TransactionsGridConfig {

    private final FormattingService formattingService;
    private final ApplicationEventPublisher eventPublisher;

    @Bean
    @UIScope
    public TransactionsGrid recentTransactionsGrid(TransactionDataProvider recentTransactionsProvider) {
        return TransactionsGrid.builder()
                .formattingService(formattingService)
                .dataProvider(recentTransactionsProvider)
                .eventPublisher(eventPublisher)
                .mode(TransactionsGrid.Mode.RECENT)
                .build();
    }

    @Bean
    @UIScope
    public TransactionsGrid allTransactionsGrid(TransactionDataProvider allTransactionsProvider) {
        return TransactionsGrid.builder()
                .formattingService(formattingService)
                .dataProvider(allTransactionsProvider)
                .eventPublisher(eventPublisher)
                .mode(TransactionsGrid.Mode.ALL)
                .build();
    }
}
