package hhz.ktoeto.moneymanager.ui.component.field;

import com.udojava.evalex.Expression;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.lang.Nullable;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class AmountInputCalculator extends AbstractFieldWithAction<BigDecimal, BigDecimalField> {

    public AmountInputCalculator() {
        super(new BigDecimalField("Сумма"), MaterialIcons.CALCULATE);
        this.getField().setLocale(Locale.of("RU", "ru"));
        this.getActionButton().setTooltipText("Режим калькулятора");

        ExpressionDialog expressionDialog = new ExpressionDialog(this::setValue);
        super.addButtonClickListener(event -> expressionDialog.open(this.getField().getValue()));
    }

    private static class ExpressionDialog extends Composite<Dialog> {

        private final TextField expressionField = new TextField("Введите выражение");
        private final Button submitButton = new Button("Рассчитать");
        private final Button cancelButton = new Button("Отмена");
        private final DecimalFormat formatter;

        private final SerializableConsumer<BigDecimal> onSubmit;

        public ExpressionDialog(SerializableConsumer<BigDecimal> onSubmit) {
            this.onSubmit = onSubmit;

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.of("RU", "ru"));
            symbols.setDecimalSeparator(',');
            this.formatter = new DecimalFormat("#0.00", symbols);
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
                String formattedValue = this.formatter.format(initialValue);
                expressionField.setValue(formattedValue);
            }
            expressionField.focus();
        }

        private void close() {
            this.getContent().close();
            expressionField.clear();
        }

        private void submit() {
            try {
                String expression = expressionField.getValue().replace(",", ".");
                BigDecimal value = new Expression(expression).eval();
                onSubmit.accept(value);
                close();
            } catch (Exception e) {
                expressionField.setErrorMessage("Некорректное выражение");
                expressionField.setInvalid(true);
            }
        }
    }
}
