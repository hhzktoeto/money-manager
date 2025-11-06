package hhz.ktoeto.moneymanager.feature.category.formview;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.formview.validator.CategoryNameValidator;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.component.IconSelector;
import hhz.ktoeto.moneymanager.ui.mixin.HasCustomErrors;

public abstract class CategoryFormView extends AbstractFormView<Category> implements HasCustomErrors {

    private final IconSelector iconSelector;
    private final TextField nameField;

    protected CategoryFormView(CategoryFormPresenter presenter) {
        super(presenter, Category.class);

        this.iconSelector = new IconSelector();
        this.nameField = new TextField("Имя");
    }

    @Override
    protected void configureRootContent(FlexLayout root) {
        this.nameField.setWidthFull();

        HorizontalLayout container = new HorizontalLayout(this.iconSelector, this.nameField);
        container.setPadding(false);
        container.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.SMALL
        );

        root.add(container);
    }

    @Override
    protected void configureBinder(Binder<Category> binder) {
        binder.forField(this.iconSelector)
                .bind(Category::getIconFileName, Category::setIconFileName);

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
