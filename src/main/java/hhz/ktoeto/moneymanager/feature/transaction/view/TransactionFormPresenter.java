package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.constant.FormMode;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreateRequested;
import hhz.ktoeto.moneymanager.ui.event.TransactionEditRequested;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionFormPresenter implements TransactionFormViewPresenter {

    private final UserContextHolder userContextHolder;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;
    private final CategoryDataProvider categoryDataProvider;

    private final CustomDialog dialog = new CustomDialog();

    private TransactionFormView view;

    @Override
    public void initialize(TransactionFormView view) {
        this.view = view;
    }

    @Override
    public void openCreateForm() {
        TransactionForm form = new TransactionForm(categoryDataProvider, this, FormMode.CREATE);

        this.dialog.setTitle("Новая транзакция");
        this.dialog.addBody(form);
        this.dialog.open();
    }

    @Override
    public void openEditForm(Transaction transaction) {
        TransactionForm form = new TransactionForm(categoryDataProvider, this, FormMode.EDIT);
        form.setEditedEntity(transaction);

        this.dialog.setTitle("Редактировать транзакцию");
        this.dialog.addBody(form);
        this.dialog.open();
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
        this.dialog.close();
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить транзакцию?");
        confirmDialog.addConfirmListener(event -> {
            Transaction transaction = view.getEditedEntity();
            transactionService.delete(transaction.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }

    @Override
    public void onCategoryAdd() {
        this.eventPublisher.publishEvent(new CategoryCreateRequested(this));
    }

    @EventListener(TransactionCreateRequested.class)
    private void onTransactionCreateRequested() {
        this.openCreateForm();
    }

    @EventListener(TransactionEditRequested.class)
    private void onTransactionEditRequested(TransactionEditRequested event) {
        this.openEditForm(event.getTransaction());
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
        this.dialog.close();
    }
}
