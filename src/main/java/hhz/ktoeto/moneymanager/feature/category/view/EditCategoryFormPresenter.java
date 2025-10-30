package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.feature.transaction.view.EditTransactionFormView;
import jakarta.annotation.PostConstruct;

@UIScope
@SpringComponent
public class EditCategoryFormPresenter extends CategoryFormPresenter {

    public EditCategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    public void initializeView() {
        this.view = new EditCategoryFormView(this);
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать категорию";
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
