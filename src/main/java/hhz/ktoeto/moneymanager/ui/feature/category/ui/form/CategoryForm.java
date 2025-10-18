package hhz.ktoeto.moneymanager.ui.feature.category.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.form.validator.CategoryNameValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class CategoryForm extends Composite<FlexLayout> {

    private final transient Consumer<CategoryForm> submitAction;
    private final transient Consumer<CategoryForm> cancelAction;

    private final Binder<Category> binder = new Binder<>(Category.class);

    @Getter
    private TextField nameField;
    private Button submitButton;
    private Button cancelButton;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

        nameField = new TextField("Имя");
        root.add(nameField);

        submitButton = new Button("Сохранить");
        submitButton.addClickListener(e -> submitAction.accept(this));

        cancelButton = new Button("Отмена");
        cancelButton.addClickListener(e -> cancelAction.accept(this));

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, submitButton);
        root.add(buttons);

        binder.forField(nameField)
                .asRequired("Имя категории не может быть пустым")
                .withValidator(new CategoryNameValidator())
                .bind(Category::getName, Category::setName);

        return root;
    }

    String name() {
        return nameField.getValue();
    }

    boolean writeTo(Category category) {
        return binder.writeBeanIfValid(category);
    }
}
