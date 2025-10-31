package hhz.ktoeto.moneymanager.feature.category.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.event.CategoryEditRequested;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class EditCategoryFormPresenter extends CategoryFormPresenter {

    public EditCategoryFormPresenter(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    public void initialize() {
        this.setView(new EditCategoryFormView(this));
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать категорию";
    }

    @Override
    public void onSubmit() {
        Category category = this.getView().getEntity();

        boolean valid = this.getView().writeToIfValid(category);
        if (!valid) {
            return;
        }

        this.getCategoryService().update(category, this.getUserContextHolder().getCurrentUserId());
        this.getRootDialog().close();
    }

    @EventListener(CategoryEditRequested.class)
    private void onCategoryEditRequested(CategoryEditRequested event) {
        this.openForm(event.getCategory());
    }
}
