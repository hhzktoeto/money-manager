package hhz.ktoeto.moneymanager.ui.feature.budget.view.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class BudgetFormViewPresenter {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;
    private final CategoryDataProvider categoryDataProvider;

    private final CustomDialog budgetFormDialog = new CustomDialog();

    @Setter
    private BudgetFormView view;

    public void openCreateForm() {
        BudgetFormView form = new BudgetFormView(categoryDataProvider, this, BudgetFormView.Mode.CREATE);

        this.budgetFormDialog.setTitle("Создать бюджет");
        this.budgetFormDialog.addBody(form);
        this.budgetFormDialog.open();
    }

    public void openEditForm(Budget budget) {
        BudgetFormView form = new BudgetFormView(categoryDataProvider, this, BudgetFormView.Mode.EDIT);
        form.setEditedBudget(budget);

        this.budgetFormDialog.setTitle("Редактировать бюджет");
        this.budgetFormDialog.addBody(form);
        this.budgetFormDialog.open();
    }

    void onSubmit() {
        if (view.isCreateMode()) {
            this.submitCreate();
        } else {
            this.submitEdit();
        }
    }

    void onCancel() {
        view.reset();
        this.budgetFormDialog.close();
    }

    void onDelete(Budget budget) {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить бюджет?");
        confirmDialog.addConfirmListener(event -> {
            budgetService.delete(budget.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.budgetFormDialog.close();
        });

        confirmDialog.open();
    }

    private void submitCreate() {
        long userId = userContextHolder.getCurrentUserId();

        Budget budget = new Budget();
        budget.setUserId(userId);

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        budgetService.create(budget);
        view.reset();
    }

    private void submitEdit() {
        Budget budget = view.getEditedBudget();

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        budgetService.update(budget, userContextHolder.getCurrentUserId());
    }
}
