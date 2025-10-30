package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormView;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class EditTransactionFormPresenter extends AbstractTransactionFormViewPresenter {

    public EditTransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                        ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        super(userContextHolder, transactionService, eventPublisher, categoryDataProvider);
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать транзакцию";
    }

    @Override
    protected TransactionFormView getForm() {
        return new EditTransactionForm(categoryDataProvider, this);
    }

    @Override
    public void onSubmit() {
        Transaction transaction = view.getEntity();

        boolean valid = view.writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        transactionService.update(transaction, userContextHolder.getCurrentUserId());
        this.dialog.close();
    }

    @EventListener(TransactionEditRequested.class)
    private void onTransactionEditRequested(TransactionEditRequested event) {
        this.openForm(event.getTransaction());
    }
}
