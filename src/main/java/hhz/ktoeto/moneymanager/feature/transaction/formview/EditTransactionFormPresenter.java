package hhz.ktoeto.moneymanager.feature.transaction.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class EditTransactionFormPresenter extends TransactionFormPresenter {

    public EditTransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                        ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        super(userContextHolder, transactionService, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initialize() {
        this.setView(new EditTransactionFormView(this, this.getCategoryDataProvider()));
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать транзакцию";
    }

    @Override
    public void onSubmit() {
        Transaction transaction = this.getView().getEntity();

        boolean valid = this.getView().writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        this.getTransactionService().update(transaction, this.getUserContextHolder().getCurrentUserId());
        this.getRootDialog().close();
    }

    @EventListener(TransactionEditRequested.class)
    private void onTransactionEditRequested(TransactionEditRequested event) {
        this.openForm(event.getTransaction());
    }
}
