package hhz.ktoeto.moneymanager.feature.category.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryFilter;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.HasCustomErrors;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateCategoryFormPresenter extends CategoryFormPresenter {

    private HasCustomErrors hasCustomErrorsDelegate;

    protected CreateCategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    public void initialize() {
        CreateCategoryFormView view = new CreateCategoryFormView(this);
        this.setView(view);
        this.hasCustomErrorsDelegate = view;
    }

    @Override
    protected String getDialogTitle() {
        return "Новая категория";
    }

    @Override
    public void onSubmit() {
        long userId = this.getUserContextHolder().getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = this.getView().writeToIfValid(category);
        if (!valid) {
            return;
        }

        CategoryFilter nameFilter = new CategoryFilter();
        nameFilter.setName(category.getName());

        if (this.getCategoryService().exist(userId, nameFilter)) {
            this.hasCustomErrorsDelegate.setError("Категория с таким именем уже существует");
            return;
        }

        this.getCategoryService().create(category);
        this.getRootDialog().close();
    }

    @EventListener(CategoryCreateRequested.class)
    private void onCategoryCreateRequested() {
        this.openForm(new Category());
    }
}
