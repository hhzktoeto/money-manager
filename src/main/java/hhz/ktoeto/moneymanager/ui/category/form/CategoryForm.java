package hhz.ktoeto.moneymanager.ui.category.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.ui.category.validator.CategoryNameValidator;

public class CategoryForm {

    private final TextField nameField = new TextField("Имя");
    private final Button submitButton = new Button("Сохранить");
    private final Button cancelButton = new Button("Отмена");

    private final Binder<Category> binder = new Binder<>(Category.class);

    CategoryForm(CategoryFormLogic logic) {
        submitButton.addClickListener(e -> logic.onSubmit(this));
        cancelButton.addClickListener(e -> logic.onCancel(this));

        binder.forField(nameField)
                .asRequired("Имя категории не может быть пустым")
                .withValidator(new CategoryNameValidator())
                .bind(Category::getName, Category::setName);
    }

    public String name() {
        return nameField.getValue();
    }

    public boolean writeTo(Category category) {
        return binder.writeBeanIfValid(category);
    }

    Components components() {
        return new Components(nameField, submitButton, cancelButton);
    }

    record Components(
            TextField nameField,
            Button submitButton,
            Button cancelButton
    ) {
    }
}
