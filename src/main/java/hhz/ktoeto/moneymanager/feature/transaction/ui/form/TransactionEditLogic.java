package hhz.ktoeto.moneymanager.feature.transaction.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionEditCancelledEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class TransactionEditLogic implements TransactionFormLogic {

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(TransactionForm form) {
        Transaction transaction = form.getEditableTransaction();
        transaction.setType(form.selectedType());

        boolean valid = form.writeTo(transaction);
        if (!valid) {
            return;
        }

        Transaction updated = transactionService.update(transaction, userContextHolder.getCurrentUserId());
        eventPublisher.publishEvent(new TransactionUpdatedEvent(this, updated));
    }

    @Override
    public void onCancel(TransactionForm form) {
        eventPublisher.publishEvent(new TransactionEditCancelledEvent(this));
    }

    @Override
    public void onCategoryAdd(TransactionForm form) {
        eventPublisher.publishEvent(new OpenCategoryCreateDialogEvent(this));
    }
}
