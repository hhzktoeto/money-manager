package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

public class IncomeExpenseToggle<T> extends CustomField<T> {

    private final FlexLayout container;
    private final Div knob;
    private final Span expenseSpan;
    private final Span incomeSpan;

    private final transient T incomeValue;

    public IncomeExpenseToggle(T expenseValue, T incomeValue) {
        this.incomeValue = incomeValue;
        setWidthFull();

        expenseSpan = new Span(expenseValue.toString());
        expenseSpan.addClassNames(
                LumoUtility.Flex.ONE,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );

        incomeSpan = new Span(incomeValue.toString());
        incomeSpan.addClassNames(
                LumoUtility.Flex.ONE,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );

        knob = new Div();
        knob.addClassNames(
                LumoUtility.Position.ABSOLUTE,
                LumoUtility.Position.Top.NONE,
                LumoUtility.Position.Bottom.NONE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BoxShadow.SMALL
        );
        knob.setWidth(50, Unit.PERCENTAGE);
        knob.getStyle().set(StyleConstants.TRANSFORM, StyleConstants.Translate.X0);
        knob.getStyle().set(StyleConstants.TRANSITION,
                StyleConstants.Transition.TRANSFORM_03_EASE + ", " + StyleConstants.Transition.BG_COLOR_03_EASE
        );

        container = new FlexLayout();
        container.addClickListener(e -> setValue(getValue().equals(expenseValue) ? incomeValue : expenseValue));
        container.addClassNames(
                LumoUtility.FlexDirection.ROW,
                LumoUtility.Position.RELATIVE,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Overflow.HIDDEN,
                LumoUtility.Gap.XSMALL,
                LumoUtility.Width.FULL,
                LumoUtility.Height.MEDIUM
        );
        container.getStyle().set(StyleConstants.TRANSITION, StyleConstants.Transition.BG_COLOR_03_EASE);
        container.add(expenseSpan, incomeSpan);
        container.add(knob);

        setValue(expenseValue);
        add(container);
    }

    @Override
    protected T generateModelValue() {
        return getValue();
    }

    @Override
    protected void setPresentationValue(T newValue) {
        boolean isIncome = newValue.equals(incomeValue);
        String containerBackground = isIncome
                ? StyleConstants.Color.SUCCESS_10
                : StyleConstants.Color.ERROR_10;
        String knobTransform = isIncome
                ? StyleConstants.Translate.X100
                : StyleConstants.Translate.X0;
        String knobBackground = isIncome
                ? StyleConstants.Color.SUCCESS
                : StyleConstants.Color.ERROR;
        String expenseSpanColor = isIncome
                ? StyleConstants.Color.SECONDARY_TEXT
                : StyleConstants.Color.ERROR_CONTRAST;
        String incomeSpanColor = isIncome
                ? StyleConstants.Color.SUCCESS_CONTRAST
                : StyleConstants.Color.SECONDARY_TEXT;

        container.getStyle().set(StyleConstants.BG_COLOR, containerBackground);
        knob.getStyle().set(StyleConstants.TRANSFORM, knobTransform);
        knob.getStyle().set(StyleConstants.BG_COLOR, knobBackground);
        expenseSpan.getStyle().set(StyleConstants.COLOR, expenseSpanColor);
        incomeSpan.getStyle().set(StyleConstants.COLOR, incomeSpanColor);
    }
}
