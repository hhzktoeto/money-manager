package hhz.ktoeto.moneymanager.core.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.core.constant.StyleConstants;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

public class TransactionTypeToggleSwitch extends Composite<Div> {

    private final HorizontalLayout container = new HorizontalLayout();
    private final Div knob = new Div();
    private final Span expenseLabel = new Span("Расход");
    private final Span incomeLabel = new Span("Доход");

    @Getter
    private Transaction.Type selectedType = Transaction.Type.EXPENSE;

    @Override
    protected Div initContent() {
        Div root = new Div();

        expenseLabel.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );

        incomeLabel.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );

        knob.setWidth(7.5f, Unit.REM);
        knob.setHeight(2, Unit.REM);
        knob.addClassNames(
                LumoUtility.Position.ABSOLUTE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BoxShadow.SMALL
        );
        knob.getStyle().set(StyleConstants.TRANSITION,
                StyleConstants.Transition.LEFT_03_EASE
                + ", "
                + StyleConstants.Transition.BG_COLOR_03_EASE
        );

        container.setWidth(246, Unit.PIXELS);
        container.setHeight(40, Unit.PIXELS);
        container.addClassNames(
                LumoUtility.Position.RELATIVE,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Overflow.HIDDEN,
                LumoUtility.Gap.XSMALL
        );
        container.getStyle().set(StyleConstants.TRANSITION, StyleConstants.Transition.BG_COLOR_03_EASE);
        container.add(expenseLabel, knob, incomeLabel);

        root.add(container);
        root.setWidth(200, Unit.PIXELS);
        root.setHeight(40, Unit.PIXELS);
        root.addClickListener(e -> {
            selectedType = (selectedType == Transaction.Type.INCOME) ? Transaction.Type.EXPENSE : Transaction.Type.INCOME;
            this.updateStyles();
        });

        this.updateStyles();

        return root;
    }

    public void setSelectedType(Transaction.Type selectedType) {
        this.selectedType = selectedType;
        this.updateStyles();
    }

    public void setWidthFull() {
        this.getContent().setWidthFull();
    }

    private void updateStyles() {
        boolean isIncome = selectedType == Transaction.Type.INCOME;
        String containerBackground = isIncome
                ? StyleConstants.Color.SUCCESS_10
                : StyleConstants.Color.ERROR_10;
        String knobLeftPosition = isIncome
                ? "123px"
                : "3px";
        String knobBackground = isIncome
                ? StyleConstants.Color.SUCCESS
                : StyleConstants.Color.ERROR;
        String expenseLabelColor = isIncome
                ? StyleConstants.Color.SECONDARY_TEXT
                : StyleConstants.Color.ERROR_CONTRAST;
        String incomeLabelColor = isIncome
                ? StyleConstants.Color.SUCCESS_CONTRAST
                : StyleConstants.Color.SECONDARY_TEXT;

        container.getStyle().set(StyleConstants.BG_COLOR, containerBackground);
        knob.getStyle().set(StyleConstants.LEFT, knobLeftPosition);
        knob.getStyle().set(StyleConstants.BG_COLOR, knobBackground);
        expenseLabel.getStyle().set(StyleConstants.COLOR, expenseLabelColor);
        incomeLabel.getStyle().set(StyleConstants.COLOR, incomeLabelColor);
    }
}
