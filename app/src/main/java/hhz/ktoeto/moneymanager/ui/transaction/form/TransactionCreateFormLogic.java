package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.transaction.entity.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.transaction.TransactionCreateComponent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionCreateFormLogic implements TransactionFormLogic {

    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionCreateComponent transactionCreateComponent;

    @Override
    public void onSubmit(TransactionForm form) {
        long userId = SecurityUtils.getCurrentUser().getId();

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);

        boolean valid = form.writeTo(transaction);
        if (!valid) {
            Notification.show("Проверьте корректность заполнения", 1500, Notification.Position.TOP_CENTER);
            return;
        }

        transactionService.create(transaction);
        eventPublisher.publishEvent(new TransactionAddedEvent(this));

        form.components().amountField().clear();
        form.components().descriptionArea().clear();
        form.components().amountField().setInvalid(false);
    }

    @Override
    public void onCancel(TransactionForm form) {
        transactionCreateComponent.close();
    }
}
