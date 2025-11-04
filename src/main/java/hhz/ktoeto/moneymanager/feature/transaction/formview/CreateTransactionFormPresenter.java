package hhz.ktoeto.moneymanager.feature.transaction.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateTransactionFormPresenter extends TransactionFormPresenter {

    public CreateTransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                          ApplicationEventPublisher eventPublisher, SimpleCategoriesProvider categoryDataProvider) {
        super(userContextHolder, transactionService, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initialize() {
        this.setView(new CreateTransactionFormView(this, this.getCategoryDataProvider()));
    }

    @Override
    protected String getDialogTitle() {
        return "Новая транзакция";
    }

    @Override
    public void onSubmit() {
        long userId = this.getUserContextHolder().getCurrentUserId();

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);

        boolean valid = this.getView().writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        Transaction saved = this.getTransactionService().create(transaction);

        Transaction resetTransaction = new Transaction();
        resetTransaction.setDate(saved.getDate());
        resetTransaction.setCategory(saved.getCategory());
        resetTransaction.setType(saved.getType());

        this.getView().setEntity(resetTransaction);
    }

    @EventListener(TransactionCreateRequested.class)
    private void onTransactionCreateRequested() {
        this.openForm(new Transaction());
    }
}
