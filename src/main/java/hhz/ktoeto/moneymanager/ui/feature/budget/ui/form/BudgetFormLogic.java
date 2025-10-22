package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.*;
import hhz.ktoeto.moneymanager.ui.feature.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.TransactionDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@SpringComponent
@RequiredArgsConstructor
public class BudgetFormLogic {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;
    private final ApplicationEventPublisher eventPublisher;

    void submitCreate(BudgetForm form) {
        long userId = userContextHolder.getCurrentUserId();

        Budget budget = new Budget();
        budget.setUserId(userId);

        boolean valid = form.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        Budget created = budgetService.create(budget);
        eventPublisher.publishEvent(new BudgetCreatedEvent(this, created));

        form.reset();
    }

    void submitEdit(BudgetForm form) {
        Budget budget = form.getEditedBudget();

        boolean valid = form.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        Budget updated = budgetService.update(budget, userContextHolder.getCurrentUserId());
        eventPublisher.publishEvent(new BudgetUpdatedEvent(this, updated));
    }

    void delete(BudgetForm form) {
        DeleteConfirmDialog dialog = new DeleteConfirmDialog();
        dialog.setHeader("Удалить бюджет?");

        dialog.addConfirmListener(event -> {
            Budget budget = form.getEditedBudget();

            budgetService.delete(budget.getId(), userContextHolder.getCurrentUserId());
            eventPublisher.publishEvent(new BudgetDeletedEvent(this));
            dialog.close();
        });

        dialog.open();
    }

    void cancelCreate() {
        eventPublisher.publishEvent(new BudgetCreationCancelledEvent(this));
    }

    void cancelEdit() {
        eventPublisher.publishEvent(new BudgetEditCancelledEvent(this));
    }

    void addCategory() {
        eventPublisher.publishEvent(new OpenCategoryCreateDialogEvent(this));
    }
}
