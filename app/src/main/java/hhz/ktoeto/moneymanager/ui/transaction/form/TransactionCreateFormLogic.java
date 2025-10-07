package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.transaction.entity.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreationCanceledEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@SpringComponent
@RequiredArgsConstructor
public class TransactionCreateFormLogic implements TransactionFormLogic {

    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(TransactionForm form) {
        long userId = SecurityUtils.getCurrentUser().getId();

        Transaction transaction = new Transaction();
        transaction.setType(form.selectedType());
        transaction.setUserId(userId);

        boolean valid = form.writeTo(transaction);
        if (!valid) {
            return;
        }

        transactionService.create(transaction);
        eventPublisher.publishEvent(new TransactionCreatedEvent(this));

        TransactionForm.Components formComponents = form.components();
        formComponents.amountField().clear();
        formComponents.descriptionArea().clear();
        formComponents.amountField().setInvalid(false);
    }

    @Override
    public void onCancel(TransactionForm form) {
        eventPublisher.publishEvent(new TransactionCreationCanceledEvent(this));
    }
}
