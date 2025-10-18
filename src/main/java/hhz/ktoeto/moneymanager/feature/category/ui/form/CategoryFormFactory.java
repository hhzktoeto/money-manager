package hhz.ktoeto.moneymanager.feature.category.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class CategoryFormFactory {

    private final CategoryFormLogic logic;

    public CategoryForm categoryCreateForm() {
        return new CategoryForm(logic::submitCreate, form -> logic.cancelCreation());
    }
}
