package hhz.ktoeto.moneymanager.feature.transaction.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateTransactionFormPresenter extends TransactionFormPresenter {

    public CreateTransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                          ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        super(userContextHolder, transactionService, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initialize() {
        this.view = new CreateTransactionFormView(this, this.categoryDataProvider);
    }

    @Override
    protected String getDialogTitle() {
        return "Новая транзакция";
    }

    @Override
    public void onSubmit() {
        long userId = userContextHolder.getCurrentUserId();

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);

        boolean valid = view.writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        Transaction saved = transactionService.create(transaction);

        Transaction resetTransaction = new Transaction();
        resetTransaction.setDate(saved.getDate());
        resetTransaction.setCategory(saved.getCategory());
        resetTransaction.setType(saved.getType());

        view.setEntity(resetTransaction);
    }

    @EventListener(TransactionCreateRequested.class)
    private void onTransactionCreateRequested() {
        this.openForm(new Transaction());
    }
}
