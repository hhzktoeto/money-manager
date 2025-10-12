package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.LayoutProvider;
import hhz.ktoeto.moneymanager.ui.category.event.CategoryCreationCancelledEvent;
import hhz.ktoeto.moneymanager.ui.category.event.OpenCategoryCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.category.form.CategoryForm;
import hhz.ktoeto.moneymanager.ui.category.form.CategoryFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;


@Slf4j
@UIScope
@SpringComponent
public class CategoryCreateDialog extends Composite<Dialog> {

    private final Button closeButton;
    private final HorizontalLayout header;

    private final Component categoryFormContainer;

    public CategoryCreateDialog(CategoryFormFactory formFactory,
                                @Qualifier("categoryCreate") LayoutProvider<CategoryForm> layoutProvider) {
        this.closeButton = new Button(VaadinIcon.CLOSE.create());
        this.header = new HorizontalLayout(new H3("Добавить категорию"), closeButton);
        this.categoryFormContainer = layoutProvider.createLayout(formFactory.createCategoryForm());
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog(header, categoryFormContainer);

        closeButton.addClickListener(e -> this.close());

        root.setCloseOnOutsideClick(false);
        return root;
    }

    @EventListener(OpenCategoryCreateDialogEvent.class)
    private void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    @EventListener(CategoryCreationCancelledEvent.class)
    private void close() {
        this.getContent().close();
    }
}
