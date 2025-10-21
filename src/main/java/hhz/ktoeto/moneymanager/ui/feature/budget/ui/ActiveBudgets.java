package hhz.ktoeto.moneymanager.ui.feature.budget.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.data.BudgetsDataProvider;

@UIScope
@SpringComponent
public class ActiveBudgets extends Composite<Scroller> implements DataProviderListener<BudgetsDataProvider> {

    private final BudgetsDataProvider dataProvider;

    private final HorizontalLayout cardsLayout;

    public ActiveBudgets(BudgetsDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.cardsLayout = new HorizontalLayout();
    }

    @Override
    protected Scroller initContent() {
        Scroller root = new Scroller();

        cardsLayout.setWidthFull();
        cardsLayout.setSpacing(false);
        cardsLayout.setPadding(false);
        cardsLayout.addClassNames(
                LumoUtility.Gap.MEDIUM,
                LumoUtility.AlignItems.STRETCH
        );

        updateCards();
        root.setContent(cardsLayout);

        return root;
    }

    @Override
    public void onDataChange(DataChangeEvent<BudgetsDataProvider> dataChangeEvent) {
        UI.getCurrent().access(this::updateCards);
    }

    private void updateCards() {
        cardsLayout.removeAll();
        dataProvider.getItems().forEach(budget -> cardsLayout.add(new BudgetCard(budget)));
    }
}
