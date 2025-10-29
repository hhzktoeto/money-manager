package hhz.ktoeto.moneymanager.feature.transaction.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import org.springframework.data.domain.Sort;

@SpringComponent
@VaadinSessionScope
public class RecentTransactionsProvider extends AbstractTransactionsDataProvider {

    public RecentTransactionsProvider(UserContextHolder userContextHolder, TransactionService transactionService) {
        super(userContextHolder, transactionService, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
