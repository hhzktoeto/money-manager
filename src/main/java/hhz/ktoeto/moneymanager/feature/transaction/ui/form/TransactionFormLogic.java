package hhz.ktoeto.moneymanager.feature.transaction.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreationCancelledEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionEditCancelledEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class TransactionFormLogic {

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;

    void submitCreate(TransactionForm form) {
        long userId = userContextHolder.getCurrentUserId();

        Transaction transaction = new Transaction();
        transaction.setType(form.selectedType());
        transaction.setUserId(userId);

        boolean valid = form.writeTo(transaction);
        if (!valid) {
            return;
        }

        Transaction saved = transactionService.create(transaction);
        eventPublisher.publishEvent(new TransactionCreatedEvent(this));

        Transaction reset = new Transaction();
        reset.setDate(saved.getDate());
        reset.setCategory(saved.getCategory());

        form.reset(reset);
    }

    void submitEdit(TransactionForm form) {
        Transaction updated = transactionService.update(form.getEditedTransaction(), userContextHolder.getCurrentUserId());
        eventPublisher.publishEvent(new TransactionUpdatedEvent(this, updated));
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
