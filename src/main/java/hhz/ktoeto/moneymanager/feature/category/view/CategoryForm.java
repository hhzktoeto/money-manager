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
import hhz.ktoeto.moneymanager.feature.category.validator.CategoryNameValidator;
import hhz.ktoeto.moneymanager.ui.constant.FormMode;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@Slf4j
public class CategoryForm extends Composite<FlexLayout> implements CategoryFormView {

    private final transient CategoryFormViewPresenter presenter;
    private final FormMode mode;

    private final TextField nameField;
    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;

    private final Binder<Category> binder;

    public CategoryForm(CategoryFormViewPresenter presenter, FormMode mode) {
        this.mode = mode;
        this.presenter = presenter;

        this.nameField = new TextField("Имя");
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

        this.binder = new Binder<>(Category.class);

        this.presenter.initialize(this);
    }

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

        this.configureNameField();
        this.configureButtonsRow(buttonsRow);
        this.configureBinder();


        root.add(
                nameField,
                buttonsRow
        );

        return root;
    }

    @Override
    public boolean isCreateMode() {
        return mode == FormMode.CREATE;
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
    public void setEditedEntity(Category category) {
        binder.setBean(category);
    }

    @Override
    public Category getEditedEntity() {
        return binder.getBean();
    }

    @Override
    public void reset(Category resetCategory) {
        binder.setBean(resetCategory);
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

    private void configureNameField() {
        this.nameField.setWidthFull();
    }

    private void configureButtonsRow(HorizontalLayout row) {
        submitButton.addClickListener(e -> presenter.onSubmit());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.addClickListener(e -> presenter.onCancel());

        deleteButton.addClickListener(e -> presenter.onDelete());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.setVisible(mode == FormMode.EDIT);

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
