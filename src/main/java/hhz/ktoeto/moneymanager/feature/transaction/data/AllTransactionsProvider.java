package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;

@SpringComponent
@VaadinSessionScope
public class AllTransactionsProvider extends AbstractTransactionsDataProvider {

    public AllTransactionsProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        super(userContextHolder, transactionService);
    }
}
