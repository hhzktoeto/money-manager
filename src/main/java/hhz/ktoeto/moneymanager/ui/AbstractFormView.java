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

import java.util.Collection;

public abstract class AbstractFormView<T> extends Composite<FlexLayout> implements FormView<T> {

    protected final transient AbstractFormViewPresenter<T> presenter;
    protected final Binder<T> binder;
    protected final Logger log;

    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;

    protected AbstractFormView(AbstractFormViewPresenter<T> presenter, Class<T> entityClass) {
        this.presenter = presenter;
        this.binder = new Binder<>(entityClass);
        this.log = LoggerFactory.getLogger(this.getClass());

        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());
    }

    protected abstract Collection<Component> getRootContent();

    protected abstract void configureBinder();

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

        Collection<Component> rootContent = this.getRootContent();
        HorizontalLayout buttonsRow = this.getButtonsRow();
        rootContent.add(buttonsRow);

        root.add(rootContent);

        this.configureBinder();

        return root;
    }

    @Override
    public boolean writeToIfValid(T entity) {
        try {
            binder.writeBean(entity);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the entity - {}", entity.getClass().getSimpleName());
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    @Override
    public void setEntity(T entity) {
        binder.setBean(entity);
    }

    @Override
    public T getEntity() {
        return binder.getBean();
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
