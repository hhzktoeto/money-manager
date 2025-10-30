package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import jakarta.annotation.PostConstruct;
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
    public void initializeView() {
        this.view = new EditTransactionFormView(this, this.categoryDataProvider);
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать транзакцию";
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
