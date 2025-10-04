package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;

import java.util.concurrent.atomic.AtomicBoolean;

@UIScope
@SpringComponent
public class TransactionTypeToggleSwitch extends Composite<Div> {

    private final AtomicBoolean incomeSelected = new AtomicBoolean(false);

    @Override
    protected Div initContent() {
        Div root = new Div();
        root.addClassName("transaction-toggle-switch");

        Div knob = new Div();
        Span leftLabel = new Span("Расход");
        leftLabel.addClassName("expense-span");
        Span rightLabel = new Span("Доход");
        rightLabel.addClassName("income-span");

        knob.addClassName("toggle-knob");

        HorizontalLayout container = new HorizontalLayout(leftLabel, knob, rightLabel);
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.addClassName("toggle-container");

        root.add(container);
        root.addClassName("expense");
        root.addClickListener(e -> {
            incomeSelected.set(!incomeSelected.get());
            if (incomeSelected.get()) {
                root.addClassName("income");
                root.removeClassName("expense");
            } else {
                root.addClassName("expense");
                root.removeClassName("income");
            }
        });

        return root;
    }

    public Transaction.Type getSelectedType() {
        return incomeSelected.get() ? Transaction.Type.INCOME : Transaction.Type.EXPENSE;
    }
}
