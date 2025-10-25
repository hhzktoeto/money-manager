package hhz.ktoeto.moneymanager.ui.feature.category.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreationCancelledEvent;
import hhz.ktoeto.moneymanager.ui.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.form.CategoryForm;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.form.CategoryFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;


@UIScope
@SpringComponent
@RequiredArgsConstructor
public class CategoryDialog extends Composite<CustomDialog> {

    private final transient CategoryFormFactory formFactory;

    @EventListener(OpenCategoryCreateDialogEvent.class)
    private void openCategoryCreation() {
        this.getContent().open();

        this.getContent().setTitle("Создать категорию");
        CategoryForm form = formFactory.categoryCreateForm();
        this.getContent().addBody(form);
    }

    @EventListener(CategoryCreationCancelledEvent.class)
    private void close() {
        this.getContent().close();
    }
}
