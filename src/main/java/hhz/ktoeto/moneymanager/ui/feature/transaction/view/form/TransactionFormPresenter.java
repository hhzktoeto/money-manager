package hhz.ktoeto.moneymanager.ui.feature.transaction.view.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.FormView;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionService;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionFormPresenter implements FormViewPresenter<Transaction, FormView<Transaction>> {

    private final UserContextHolder userContextHolder;
    private final TransactionService transactionService;
    private final CategoryDataProvider categoryDataProvider;

    private final CustomDialog transactionFormDialog = new CustomDialog();

    private FormView<Transaction> view;

    @Override
    public void setView(FormView<Transaction> view) {
        this.view = view;
    }

    @Override
    public void openCreateForm() {
        TransactionForm form = new TransactionForm(categoryDataProvider, this, TransactionForm.Mode.CREATE);

        this.transactionFormDialog.setTitle("Новая транзакция");
        this.transactionFormDialog.addBody(form);
        this.transactionFormDialog.open();
    }

    @Override
    public void openEditForm(Transaction transaction) {
        TransactionForm form = new TransactionForm(categoryDataProvider, this, TransactionForm.Mode.EDIT);
        form.setEditedEntity(transaction);

        this.transactionFormDialog.setTitle("Редактировать транзакцию");
        this.transactionFormDialog.addBody(form);
        this.transactionFormDialog.open();
    }

    @Override
    public void onSubmit() {
        if (view.isCreateMode()) {
            this.submitCreate();
        } else {
            this.submitEdit();
        }
    }

    @Override
    public void onCancel() {
        this.transactionFormDialog.close();
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить транзакцию?");
        confirmDialog.addConfirmListener(event -> {
            Transaction transaction = view.getEditedEntity();
            transactionService.delete(transaction.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.transactionFormDialog.close();
        });

        confirmDialog.open();
    }

    private void submitCreate() {
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

        view.reset(resetTransaction);
    }

    private void submitEdit() {
        Transaction transaction = view.getEditedEntity();

        boolean valid = view.writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        transactionService.update(transaction, userContextHolder.getCurrentUserId());
    }
}
