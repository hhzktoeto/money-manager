package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormView;
import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.view.validator.CategoryNameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

public abstract class AbstractCategoryFormView extends Composite<FlexLayout> implements CategoryFormView {

    protected final transient CategoryFormViewPresenter presenter;
    protected final Binder<Category> binder;

    private final TextField nameField;
    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;

    private final Logger log;

    protected AbstractCategoryFormView(CategoryFormViewPresenter presenter) {
        this.presenter = presenter;
        this.binder = new Binder<>(Category.class);

        this.nameField = new TextField("Имя");
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

        this.log = LoggerFactory.getLogger(this.getClass());

        this.presenter.initialize(this);
    }

    protected abstract boolean isDeleteButtonVisible();

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.MaxWidth.SCREEN_SMALL,
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        HorizontalLayout buttonsRow = new HorizontalLayout();

        this.nameField.setWidthFull();
        this.configureButtonsRow(buttonsRow);
        this.configureBinder();

        root.add(
                nameField,
                buttonsRow
        );

        return root;
    }

    @Override
    public boolean writeToIfValid(Category category) {
        try {
            binder.writeBean(category);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the category");
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    @Override
    public void setEntity(Category category) {
        binder.setBean(category);
    }

    @Override
    public Category getEntity() {
        return binder.getBean();
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void setError(String error) {
        this.nameField.setErrorMessage(error);
        this.nameField.setInvalid(true);
    }

    private void configureButtonsRow(HorizontalLayout row) {
        submitButton.addClickListener(e -> presenter.onSubmit());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.addClickListener(e -> presenter.onCancel());

        deleteButton.addClickListener(e -> presenter.onDelete());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.setVisible(isDeleteButtonVisible());

        HorizontalLayout submitCancelLayout = new HorizontalLayout(cancelButton, submitButton);
        submitCancelLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.END,
                LumoUtility.Gap.MEDIUM
        );

        row.add(deleteButton, submitCancelLayout);
        row.addClassNames(
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN
        );
    }

    private void configureBinder() {
        binder.forField(nameField)
                .withValidator(new CategoryNameValidator())
                .bind(Category::getName, (category, name) -> category.setName(name.trim()));
    }
}
