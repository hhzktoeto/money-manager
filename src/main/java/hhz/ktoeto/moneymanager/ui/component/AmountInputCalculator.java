package hhz.ktoeto.moneymanager.ui.component;

import com.udojava.evalex.Expression;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.lang.Nullable;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.math.BigDecimal;

public class AmountInputCalculator extends CustomField<BigDecimal> {

    private final BigDecimalField numberField;
    private final Button calculateButton;
    private final ExpressionDialog expressionDialog;

    private final HorizontalLayout container;

    public AmountInputCalculator() {
        this.numberField = new BigDecimalField("Сумма");
        this.calculateButton = new Button(MaterialIcons.CALCULATE.create());
        this.expressionDialog = new ExpressionDialog(this::setValue);

        this.container = new HorizontalLayout(this.numberField, this.calculateButton);

        this.numberField.setWidthFull();

        this.calculateButton.setTooltipText("Режим калькулятора");
        this.calculateButton.addClickListener(event -> this.expressionDialog.open(this.numberField.getValue()));

        this.container.setPadding(false);
        this.container.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        this.setWidthFull();
        this.add(this.container);
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        this.numberField.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public void setInvalid(boolean invalid) {
        this.numberField.setInvalid(invalid);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.numberField.setErrorMessage(errorMessage);
    }

    @Override
    protected BigDecimal generateModelValue() {
        return numberField.getValue();
    }

    @Override
    protected void setPresentationValue(BigDecimal bigDecimal) {
        numberField.setValue(bigDecimal);
    }

    private static class ExpressionDialog extends Composite<Dialog> {

        private final TextField expressionField = new TextField("Введите выражение");
        private final Button submitButton = new Button("Рассчитать");
        private final Button cancelButton = new Button("Отмена");

        private final SerializableConsumer<BigDecimal> onSubmit;

        public ExpressionDialog(SerializableConsumer<BigDecimal> onSubmit) {
            this.onSubmit = onSubmit;
        }

        @Override
        protected Dialog initContent() {
            Dialog root = new Dialog();
            root.setCloseOnOutsideClick(false);

            expressionField.setWidthFull();
            expressionField.addKeyPressListener(Key.ENTER, event -> this.submit());
            root.add(expressionField);

            submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            submitButton.addClickListener(event -> this.submit());

            cancelButton.addClickListener(event -> this.close());

            HorizontalLayout buttons = new HorizontalLayout(cancelButton, submitButton);
            buttons.setWidthFull();
            buttons.addClassNames(
                    LumoUtility.AlignItems.STRETCH,
                    LumoUtility.Margin.Top.MEDIUM,
                    LumoUtility.JustifyContent.BETWEEN,
                    LumoUtility.Gap.LARGE
            );
            root.add(buttons);

            return root;
        }

        private void open(@Nullable BigDecimal initialValue) {
            this.getContent().open();
            if (initialValue != null) {
                expressionField.setValue(String.valueOf(initialValue.doubleValue()));
            }
            expressionField.focus();
        }

        private void close() {
            this.getContent().close();
            expressionField.clear();
        }

        private void submit() {
            try {
                BigDecimal value = new Expression(expressionField.getValue()).eval();
                onSubmit.accept(value);
                close();
            } catch (Exception e) {
                expressionField.setErrorMessage("Некорректное выражение");
                expressionField.setInvalid(true);
            }
        }
    }
}
