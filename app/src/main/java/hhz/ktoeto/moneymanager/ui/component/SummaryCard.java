package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.html.Div;

public class SummaryCard extends Div {
    public SummaryCard() {
        setText("Сводка: баланс, расходы, доходы...");
        addClassName("summary-card");
    }
}
