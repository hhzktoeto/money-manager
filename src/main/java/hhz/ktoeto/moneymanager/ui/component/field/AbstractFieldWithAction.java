package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.AccessLevel;
import lombok.Getter;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;


public abstract class AbstractFieldWithAction<V, F extends Component & HasValue<?, V> & HasValidation & HasHelper & HasSize>
        extends CustomField<V> {

    @Getter(AccessLevel.PROTECTED)
    private final F field;
    @Getter(AccessLevel.PROTECTED)
    private final Button actionButton;

    public AbstractFieldWithAction(F field, MaterialIcons buttonIcon) {
        this.field = field;
        this.actionButton = new Button(buttonIcon.create());
        HorizontalLayout container = new HorizontalLayout(this.field, this.actionButton);

        this.field.setWidthFull();

        container.setPadding(false);
        container.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        this.setWidthFull();
        this.add(container);
    }

    public void addButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        this.actionButton.addClickListener(listener);
    }

    @Override
    protected V generateModelValue() {
        return this.field.getValue();
    }

    @Override
    protected void setPresentationValue(V newValue) {
        this.field.setValue(newValue);
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        this.field.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        this.field.setInvalid(invalid);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
        this.field.setErrorMessage(null);
    }

    @Override
    public String getErrorMessage() {
        return this.field.getErrorMessage();
    }

    @Override
    public boolean isInvalid() {
        return super.isInvalid() || this.field.isInvalid();
    }
}
