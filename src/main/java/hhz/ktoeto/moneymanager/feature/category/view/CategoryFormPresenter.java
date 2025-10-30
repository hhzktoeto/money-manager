package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;

public abstract class CategoryFormPresenter extends AbstractFormViewPresenter<Category> {

    protected final CategoryService categoryService;
    protected final UserContextHolder userContextHolder;

    protected final CustomDialog dialog = new CustomDialog();

    protected CategoryFormView view;

    protected CategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        this.categoryService = categoryService;
        this.userContextHolder = userContextHolder;
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
