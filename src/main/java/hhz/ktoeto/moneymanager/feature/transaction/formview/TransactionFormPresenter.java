package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;
import org.springframework.context.ApplicationEventPublisher;

public abstract class TransactionFormPresenter extends AbstractFormViewPresenter<Transaction> implements CanAddCategory {

    protected final UserContextHolder userContextHolder;
    protected final TransactionService transactionService;
    protected final ApplicationEventPublisher eventPublisher;
    protected final CategoryDataProvider categoryDataProvider;

    protected TransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                       ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
        this.categoryDataProvider = categoryDataProvider;
    }

    @Override
    public void onCategoryAdd() {
        this.eventPublisher.publishEvent(new CategoryCreateRequested(this));
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить транзакцию?");
        confirmDialog.addConfirmListener(event -> {
            Transaction transaction = this.view.getEntity();
            transactionService.delete(transaction.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }
}
