package hhz.ktoeto.moneymanager.ui.category.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class CategoryFormFactory {

    private final CategoryCreateFormLogic createLogic;

    public CategoryForm createCategoryForm() {
        return new CategoryForm(createLogic);
    }
}
