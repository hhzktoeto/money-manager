package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormView;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;

public abstract class AbstractCategoryFormViewPresenter implements CategoryFormViewPresenter {

    protected final CategoryService categoryService;
    protected final UserContextHolder userContextHolder;

    protected final CustomDialog dialog = new CustomDialog();

    protected CategoryFormView view;

    protected AbstractCategoryFormViewPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        this.categoryService = categoryService;
        this.userContextHolder = userContextHolder;
    }

    protected abstract String getDialogTitle();

    protected abstract CategoryFormView getForm();

    @Override
    public void initialize(CategoryFormView view) {
        this.view = view;
    }

    @Override
    public void openForm(Category category) {
        CategoryFormView form = this.getForm();
        form.setEntity(category);

        this.dialog.setTitle(this.getDialogTitle());
        this.dialog.addBody(form.asComponent());
        this.dialog.open();
    }

    @Override
    public void onCancel() {
        this.dialog.close();
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить категорию?");
        confirmDialog.setText("Все транзакции, цели и бюджеты так же будут удалены");
        confirmDialog.addConfirmListener(event -> {
            Category category = view.getEntity();
            categoryService.delete(category.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }
}
