package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.utils.StylingUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class TransactionTypeToggleSwitch extends Composite<Div> {

    private final AtomicBoolean incomeSelected = new AtomicBoolean(false);

    private HorizontalLayout container;
    private Div knob;
    private Span expenseLabel;
    private Span incomeLabel;

    @Override
    protected Div initContent() {
        Div root = new Div();

        expenseLabel = new Span("Расход");
        expenseLabel.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );
        incomeLabel = new Span("Доход");
        incomeLabel.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.ZIndex.XSMALL,
                LumoUtility.Transition.COLORS
        );

        knob = new Div();
        knob.setWidth(7.5f, Unit.REM);
        knob.setHeight(2, Unit.REM);
        knob.addClassNames(
                LumoUtility.Position.ABSOLUTE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BoxShadow.SMALL
        );
        knob.getStyle().set(StylingUtils.TRANSITION,
                StylingUtils.Transition.LEFT_03_EASE
                + ", "
                + StylingUtils.Transition.BG_COLOR_03_EASE
        );

        container = new HorizontalLayout(expenseLabel, knob, incomeLabel);
        container.setWidth(246, Unit.PIXELS);
        container.setHeight(40, Unit.PIXELS);
        container.addClassNames(
                LumoUtility.Position.RELATIVE,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Overflow.HIDDEN,
                LumoUtility.Gap.XSMALL
        );
        container.getStyle().set(StylingUtils.TRANSITION, StylingUtils.Transition.BG_COLOR_03_EASE);

        root.add(container);
        root.setWidth(200, Unit.PIXELS);
        root.setHeight(40, Unit.PIXELS);
        root.addClickListener(e -> {
            incomeSelected.set(!incomeSelected.get());
            updateStyles();
        });

        updateStyles();

        return root;
    }

    public Transaction.Type getSelectedType() {
        return incomeSelected.get() ? Transaction.Type.INCOME : Transaction.Type.EXPENSE;
    }

    public void setWidthFull() {
        this.getContent().setWidthFull();
    }

    private void updateStyles() {
        boolean isIncome = incomeSelected.get();
        String containerBackground = isIncome
                ? StylingUtils.Color.SUCCESS_10
                : StylingUtils.Color.ERROR_10;
        String knobLeftPosition = isIncome
                ? "123px"
                : "3px";
        String knobBackground = isIncome
                ? StylingUtils.Color.SUCCESS
                : StylingUtils.Color.ERROR;
        String expenseLabelColor = isIncome
                ? StylingUtils.Color.SECONDARY_TEXT
                : StylingUtils.Color.ERROR_CONTRAST;
        String incomeLabelColor = isIncome
                ? StylingUtils.Color.SUCCESS_CONTRAST
                : StylingUtils.Color.SECONDARY_TEXT;

        container.getStyle().set(StylingUtils.BG_COLOR, containerBackground);
        knob.getStyle().set(StylingUtils.LEFT, knobLeftPosition);
        knob.getStyle().set(StylingUtils.BG_COLOR, knobBackground);
        expenseLabel.getStyle().set(StylingUtils.COLOR, expenseLabelColor);
        incomeLabel.getStyle().set(StylingUtils.COLOR, incomeLabelColor);
    }
}
