package hhz.ktoeto.moneymanager.feature.category.formview;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.formview.validator.CategoryNameValidator;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.mixin.HasCustomErrors;

public abstract class CategoryFormView extends AbstractFormView<Category> implements HasCustomErrors {

    private final TextField nameField;

    protected CategoryFormView(CategoryFormPresenter presenter) {
        super(presenter, Category.class);

        this.nameField = new TextField("Имя");
    }

    @Override
    protected void configureRootContent(FlexLayout root) {
        this.nameField.setWidthFull();
        root.add(this.nameField);
    }

    @Override
    protected void configureBinder(Binder<Category> binder) {
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
