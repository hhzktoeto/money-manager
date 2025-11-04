package hhz.ktoeto.moneymanager.feature.budget.formview;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BudgetFormPresenter extends AbstractFormViewPresenter<Budget> implements CanAddCategory {

    private final transient ApplicationEventPublisher eventPublisher;
    @Getter(AccessLevel.PROTECTED)
    private final transient BudgetService budgetService;
    @Getter(AccessLevel.PROTECTED)
    private final transient UserContextHolder userContextHolder;
    @Getter(AccessLevel.PROTECTED)
    private final SimpleCategoriesProvider categoryDataProvider;

    protected BudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                  ApplicationEventPublisher eventPublisher, SimpleCategoriesProvider categoryDataProvider) {
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
            Budget budget = this.getView().getEntity();
            budgetService.delete(budget.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.getRootDialog().close();
        });

        confirmDialog.open();
    }
}
