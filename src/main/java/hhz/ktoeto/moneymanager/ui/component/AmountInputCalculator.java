package hhz.ktoeto.moneymanager.ui.component;

import com.udojava.evalex.Expression;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class AmountInputCalculator extends CustomField<BigDecimal> {

    private final NumberField numberField;
    private final ExpressionDialog expressionDialog;
    private final Button calculateButton;

    public AmountInputCalculator() {
        setWidthFull();

        numberField = new NumberField("Сумма");
        numberField.setWidthFull();

        expressionDialog = new ExpressionDialog(this::setValue);

        calculateButton = new Button(VaadinIcon.CALC.create());
        calculateButton.setTooltipText("Режим калькулятора");
        calculateButton.addClickListener(event -> expressionDialog.open());

        HorizontalLayout container = new HorizontalLayout(numberField, calculateButton);
        container.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Margin.Bottom.XSMALL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        add(container);
    }

    @Override
    protected BigDecimal generateModelValue() {
        Double value = numberField.getValue();
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    @Override
    protected void setPresentationValue(BigDecimal bigDecimal) {
        numberField.setValue(bigDecimal != null ? bigDecimal.doubleValue() : null);
    }

    private static class ExpressionDialog extends Composite<Dialog> {

        private final TextField expressionField = new TextField("Введите выражение");
        private final Button submitButton = new Button("Рассчитать");
        private final Button cancelButton = new Button("Отмена");

        private final Consumer<BigDecimal> onSubmit;

        public ExpressionDialog(Consumer<BigDecimal> onSubmit) {
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

        void open() {
            this.getContent().open();
            expressionField.focus();
        }

        void close() {
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
