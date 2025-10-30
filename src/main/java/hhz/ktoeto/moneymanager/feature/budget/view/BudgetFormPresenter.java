package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BudgetFormPresenter extends AbstractFormViewPresenter<Budget> implements CanAddCategory {

    protected final BudgetService budgetService;
    protected final UserContextHolder userContextHolder;
    protected final ApplicationEventPublisher eventPublisher;
    protected final CategoryDataProvider categoryDataProvider;

    protected BudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                  ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
        this.eventPublisher = eventPublisher;
        this.categoryDataProvider = categoryDataProvider;
    }

    @Override
    public void onCategoryAdd() {
        this.eventPublisher.publishEvent(new CategoryCreateRequested(this));
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить бюджет?");
        confirmDialog.addConfirmListener(event -> {
            Budget budget = view.getEntity();
            budgetService.delete(budget.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }
}
