package hhz.ktoeto.moneymanager.feature.budget.presenter;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormView;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.budget.view.BudgetForm;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.constant.FormMode;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class BudgetFormPresenter implements BudgetFormViewPresenter {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;
    private final CategoryDataProvider categoryDataProvider;
    private final CategoryFormViewPresenter categoryFormPresenter;

    private final CustomDialog budgetFormDialog = new CustomDialog();

    private BudgetFormView view;

    @Override
    public void setView(BudgetFormView view) {
        this.view = view;
    }

    @Override
    public void openCreateForm() {
        BudgetForm form = new BudgetForm(categoryDataProvider, this, FormMode.CREATE);

        this.budgetFormDialog.setTitle("Новый бюджет");
        this.budgetFormDialog.addBody(form);
        this.budgetFormDialog.open();
    }

    @Override
    public void openEditForm(Budget budget) {
        BudgetForm form = new BudgetForm(categoryDataProvider, this, FormMode.EDIT);
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
        view.reset(new Budget());
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


    @Override
    public void onCategoryAdd() {
        categoryFormPresenter.openCreateForm();
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
        view.reset(new Budget());
        this.budgetFormDialog.close();
    }

    private void submitEdit() {
        Budget budget = view.getEditedEntity();

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        budgetService.update(budget, userContextHolder.getCurrentUserId());
        this.budgetFormDialog.close();
    }
}
