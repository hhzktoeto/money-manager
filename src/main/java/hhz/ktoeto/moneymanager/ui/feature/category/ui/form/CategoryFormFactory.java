package hhz.ktoeto.moneymanager.ui.feature.category.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class CategoryFormFactory {

    private final CategoryFormLogic logic;

    public CategoryForm categoryCreateForm() {
        CategoryForm form = new CategoryForm(logic::submitCreate, logic::cancelCreation);
        logic.setForm(form);

        return form;
    }
}
