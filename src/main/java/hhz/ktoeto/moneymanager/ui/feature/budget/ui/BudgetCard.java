package hhz.ktoeto.moneymanager.ui.feature.budget.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Span;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetCard extends Composite<BasicContainer> {

    private final Budget budget;

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();
        root.setHeader(budget.getName());

        root.setWidthFull();
        root.setMaxWidth(400, Unit.PIXELS);
        root.setMinWidth(285, Unit.PIXELS);

        root.setContent(new Span(budget.toString()));

        return root;
    }
}
