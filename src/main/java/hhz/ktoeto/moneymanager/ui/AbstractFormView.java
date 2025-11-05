package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

public abstract class AbstractFormView<T> extends Composite<FlexLayout> implements FormView<T> {

    private final transient AbstractFormViewPresenter<T> presenter;
    private final Logger log;

    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;

    private final Binder<T> binder;

    protected AbstractFormView(AbstractFormViewPresenter<T> presenter, Class<T> entityClass) {
        this.presenter = presenter;
        this.log = LoggerFactory.getLogger(this.getClass());

        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

        this.binder = new Binder<>(entityClass);
        this.configureBinder(this.binder);
    }

    protected abstract void configureRootContent(FlexLayout root);

    protected abstract void configureBinder(Binder<T> binder);

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

        this.configureRootContent(root);

        HorizontalLayout buttonsRow = this.getButtonsRow();
        root.add(buttonsRow);

        return root;
    }

    @Override
    public boolean writeToIfValid(T entity) {
        try {
            this.binder.writeBean(entity);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the entity - {}", entity.getClass().getSimpleName());
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    @Override
    public void setEntity(T entity) {
        this.binder.readBean(entity);
    }

    @Override
    public T getEntity() {
        return this.binder.getBean();
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private HorizontalLayout getButtonsRow() {
        HorizontalLayout row = new HorizontalLayout();
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

        return row;
    }
}
