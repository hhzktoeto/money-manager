package hhz.ktoeto.moneymanager.ui.feature.budget.view.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.FormView;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class BudgetFormPresenter implements FormViewPresenter<Budget, FormView<Budget>> {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;
    private final CategoryDataProvider categoryDataProvider;

    private final CustomDialog budgetFormDialog = new CustomDialog();

    private FormView<Budget> view;

    @Override
    public void setView(FormView<Budget> view) {
        this.view = view;
    }

    @Override
    public void openCreateForm() {
        BudgetForm form = new BudgetForm(categoryDataProvider, this, BudgetForm.Mode.CREATE);

        this.budgetFormDialog.setTitle("Создать бюджет");
        this.budgetFormDialog.addBody(form);
        this.budgetFormDialog.open();
    }

    @Override
    public void openEditForm(Budget budget) {
        BudgetForm form = new BudgetForm(categoryDataProvider, this, BudgetForm.Mode.EDIT);
        form.setEditedEntity(budget);

        this.budgetFormDialog.setTitle("Редактировать бюджет");
        this.budgetFormDialog.addBody(form);
        this.budgetFormDialog.open();
    }

    @Override
    public void onSubmit() {
        if (view.isCreateMode()) {
            this.submitCreate();
        } else {
            this.submitEdit();
        }
    }

    @Override
    public void onCancel() {
        view.reset();
        this.budgetFormDialog.close();
    }

    @Override
    public void onDelete() {

        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить бюджет?");
        confirmDialog.addConfirmListener(event -> {
            Budget budget = view.getEditedEntity();
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
        Budget budget = view.getEditedEntity();

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        budgetService.update(budget, userContextHolder.getCurrentUserId());
    }
}
