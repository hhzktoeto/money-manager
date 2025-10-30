package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.util.List;

public abstract class BudgetsCardsView extends Composite<Div> implements View, HasUpdatableData<List<BudgetCard>> {

    protected final transient BudgetsCardsPresenter presenter;

    public BudgetsCardsView(BudgetsCardsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Div initContent() {
        Div root = new Div();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.Grid.Breakpoint.Large.COLUMNS_3,
                LumoUtility.AlignContent.START,
                LumoUtility.AlignItems.STRETCH
        );

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }
}
