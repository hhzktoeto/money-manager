package hhz.ktoeto.moneymanager.feature.transaction.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import org.springframework.context.ApplicationEventPublisher;

public abstract class AbstractTransactionFormViewPresenter implements TransactionFormViewPresenter {

    protected final UserContextHolder userContextHolder;
    protected final TransactionService transactionService;
    protected final ApplicationEventPublisher eventPublisher;
    protected final CategoryDataProvider categoryDataProvider;

    protected final CustomDialog dialog = new CustomDialog();

    protected TransactionFormView view;

    protected AbstractTransactionFormViewPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                                ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
        this.categoryDataProvider = categoryDataProvider;
    }

    protected abstract String getDialogTitle();

    protected abstract TransactionFormView getForm();

    @Override
    public void initialize(TransactionFormView view) {
        this.view = view;
    }

    @Override
    public void openForm(Transaction transaction) {
        TransactionFormView form = this.getForm();
        form.setEntity(transaction);

        this.dialog.setTitle(this.getDialogTitle());
        this.dialog.addBody(form.asComponent());
        this.dialog.open();
    }

    @Override
    public void onCancel() {
        this.dialog.close();
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
            Transaction transaction = view.getEntity();
            transactionService.delete(transaction.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }
}
