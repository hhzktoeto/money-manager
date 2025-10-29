package hhz.ktoeto.moneymanager.feature.category.presenter;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormView;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryFilter;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.feature.category.view.CategoryForm;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.constant.FormMode;
import lombok.RequiredArgsConstructor;

@SpringComponent
@RequiredArgsConstructor
public class CategoryFormPresenter implements CategoryFormViewPresenter {

    private final CategoryService categoryService;
    private final UserContextHolder userContextHolder;

    private final CustomDialog dialog = new CustomDialog();

    private CategoryFormView view;

    @Override
    public void initialize(CategoryFormView view) {
        this.view = view;
    }

    @Override
    public void openCreateForm() {
        CategoryForm form = new CategoryForm(this, FormMode.CREATE);

        this.dialog.setTitle("Новая категория");
        this.dialog.addBody(form);
        this.dialog.open();
    }

    @Override
    public void openEditForm(Category category) {
        CategoryForm form = new CategoryForm(this, FormMode.CREATE);
        form.setEditedEntity(category);

        this.dialog.setTitle("Редактировать категорию");
        this.dialog.addBody(form);
        this.dialog.open();
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
        this.dialog.close();
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить категорию?");
        confirmDialog.setText("Все транзакции, цели и бюджеты так же будут удалены");
        confirmDialog.addConfirmListener(event -> {
            Category category = view.getEditedEntity();
            categoryService.delete(category.getId(), userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.dialog.close();
        });

        confirmDialog.open();
    }

    public void submitCreate() {
        long userId = userContextHolder.getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = view.writeToIfValid(category);
        if (!valid) {
            return;
        }

        CategoryFilter nameFilter = new CategoryFilter();
        nameFilter.setName(category.getName());

        if (categoryService.exist(userId, nameFilter)) {
            view.setError("Категория с таким именем уже существует");
            return;
        }

        categoryService.create(category);
        this.dialog.close();
    }

    private void submitEdit() {
        Category category = view.getEditedEntity();

        boolean valid = view.writeToIfValid(category);
        if (!valid) {
            return;
        }

        categoryService.update(category, userContextHolder.getCurrentUserId());
        this.dialog.close();
    }
}
