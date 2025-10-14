package hhz.ktoeto.moneymanager.feature.transaction.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreationCancelledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class TransactionCreateLogic implements TransactionFormLogic {

    private final transient UserContextHolder userContextHolder;
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(TransactionForm form) {
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

    @Override
    public void onCancel(TransactionForm form) {
        eventPublisher.publishEvent(new TransactionCreationCancelledEvent(this));
    }

    @Override
    public void onCategoryAdd(TransactionForm form) {
        eventPublisher.publishEvent(new OpenCategoryCreateDialogEvent(this));
    }
}
