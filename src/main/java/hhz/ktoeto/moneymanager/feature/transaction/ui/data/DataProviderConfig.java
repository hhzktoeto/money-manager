package hhz.ktoeto.moneymanager.feature.transaction.ui.data;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataProviderConfig {

    private final UserContextHolder userContextHolder;
    private final TransactionService transactionService;

    @Bean
    @VaadinSessionScope
    public TransactionDataProvider allTransactionsProvider() {
        return new TransactionDataProvider(transactionService, userContextHolder, Integer.MAX_VALUE);
    }

    @Bean
    @VaadinSessionScope
    public TransactionDataProvider recentTransactionsProvider() {
        return new TransactionDataProvider(transactionService, userContextHolder, 5);
    }
}
