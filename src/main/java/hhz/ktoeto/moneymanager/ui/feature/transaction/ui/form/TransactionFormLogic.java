package hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.feature.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class TransactionFormLogic {

    private final UserContextHolder userContextHolder;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    void submitCreate(TransactionForm form) {
        long userId = userContextHolder.getCurrentUserId();

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);

        boolean valid = form.writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        Transaction saved = transactionService.create(transaction);
        eventPublisher.publishEvent(new TransactionCreatedEvent(this));

        form.reset(saved.getDate(), saved.getCategory(), saved.getType());
    }

    void submitEdit(TransactionForm form) {
        Transaction transaction = form.getEditedTransaction();

        boolean valid = form.writeToIfValid(transaction);
        if (!valid) {
            return;
        }

        Transaction updated = transactionService.update(transaction, userContextHolder.getCurrentUserId());
        eventPublisher.publishEvent(new TransactionUpdatedEvent(this, updated));
    }

    void delete(TransactionForm form) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Удалить транзакцию?");

        dialog.setCancelable(true);
        dialog.setCancelText("Ой, нет");

        dialog.setConfirmText("Да");
        dialog.addConfirmListener(event -> {
            Transaction transaction = form.getEditedTransaction();

            transactionService.delete(transaction.getId(), userContextHolder.getCurrentUserId());
            eventPublisher.publishEvent(new TransactionDeletedEvent(this));
            dialog.close();
        });

        dialog.open();
    }

    void cancelCreate() {
        eventPublisher.publishEvent(new TransactionCreationCancelledEvent(this));
    }

    void cancelEdit() {
        eventPublisher.publishEvent(new TransactionEditCancelledEvent(this));
    }

    void addCategory() {
        eventPublisher.publishEvent(new OpenCategoryCreateDialogEvent(this));
    }
}
