package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.backend.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionEditCancelledEvent;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class TransactionEditLogic implements TransactionFormLogic {

    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(TransactionForm form) {
        Transaction transaction = form.getEditableTransaction();
        transaction.setType(form.selectedType());

        boolean valid = form.writeTo(transaction);
        if (!valid) {
            return;
        }

        Transaction updated = transactionService.update(transaction, SecurityUtils.getCurrentUserId());
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
