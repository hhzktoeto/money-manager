package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.view.validator.CategoryNameValidator;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.mixin.HasCustomErrors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CategoryFormView extends AbstractFormView<Category> implements HasCustomErrors {

    private final TextField nameField;

    protected CategoryFormView(CategoryFormPresenter presenter) {
        super(presenter, Category.class);

        this.nameField = new TextField("Имя");

        presenter.initialize(this);
    }

    @Override
    protected Collection<Component> getRootContent() {
        this.nameField.setWidthFull();

        return new ArrayList<>(List.of(nameField));
    }

    @Override
    protected void configureBinder() {
        binder.forField(nameField)
                .withValidator(new CategoryNameValidator())
                .bind(Category::getName, (category, name) -> category.setName(name.trim()));
    }

    @Override
    public void setError(String error) {
        this.nameField.setErrorMessage(error);
        this.nameField.setInvalid(true);
    }
}
