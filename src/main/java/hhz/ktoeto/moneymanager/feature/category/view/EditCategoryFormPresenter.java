package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormView;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;

@UIScope
@SpringComponent
public class EditCategoryFormPresenter extends AbstractCategoryFormViewPresenter {

    public EditCategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать категорию";
    }

    @Override
    protected CategoryFormView getForm() {
        return new EditCategoryForm(this);
    }

    @Override
    public void onSubmit() {
        Category category = this.view.getEntity();

        boolean valid = this.view.writeToIfValid(category);
        if (!valid) {
            return;
        }

        this.categoryService.update(category, this.userContextHolder.getCurrentUserId());
        this.dialog.close();
    }
}
