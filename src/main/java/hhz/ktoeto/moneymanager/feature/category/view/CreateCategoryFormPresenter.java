package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryFilter;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateCategoryFormPresenter extends CategoryFormPresenter {

    protected CreateCategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    protected String getDialogTitle() {
        return "Новая категория";
    }

    @Override
    protected AbstractFormView<Category> getForm() {
        return new CreateCategoryFormView(this);
    }

    @Override
    public void onSubmit() {
        long userId = this.userContextHolder.getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = this.view.writeToIfValid(category);
        if (!valid) {
            return;
        }

        CategoryFilter nameFilter = new CategoryFilter();
        nameFilter.setName(category.getName());

        if (this.categoryService.exist(userId, nameFilter)) {
            this.view.setError("Категория с таким именем уже существует");
            return;
        }

        this.categoryService.create(category);
        this.dialog.close();
    }

    @EventListener(CategoryCreateRequested.class)
    private void onCategoryCreateRequested() {
        this.openForm(new Category());
    }
}
