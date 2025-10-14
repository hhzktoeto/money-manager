package hhz.ktoeto.moneymanager.feature.category.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.ui.LayoutProvider;

@UIScope
@SpringComponent("categoryCreate")
public class CategoryCreateFormLayoutProvider implements LayoutProvider<CategoryForm> {

    @Override
    public Component createLayout(CategoryForm form) {
        VerticalLayout layout = new VerticalLayout();
        CategoryForm.Components components = form.components();
        HorizontalLayout firstRow = new HorizontalLayout(
                components.nameField()
        );
        HorizontalLayout buttons = new HorizontalLayout(
                components.cancelButton(),
                components.submitButton()
        );

        layout.add(firstRow, buttons);

        return layout;
    }
}
