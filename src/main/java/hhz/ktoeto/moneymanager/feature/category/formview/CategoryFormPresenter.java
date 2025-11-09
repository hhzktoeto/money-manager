package hhz.ktoeto.moneymanager.feature.category.formview;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
public abstract class CategoryFormPresenter extends AbstractFormViewPresenter<Category> {

    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;

    protected CategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        this.categoryService = categoryService;
        this.userContextHolder = userContextHolder;
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить категорию?");
        confirmDialog.setText("Все транзакции, связанные с категорией, так же будут удалены");
        confirmDialog.addConfirmListener(event -> {
            Category category = this.getView().getEntity();
            this.categoryService.delete(category.getId(), this.userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.getRootDialog().close();
        });

        confirmDialog.open();
    }
}
