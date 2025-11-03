package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

public class FavouriteBudgetsCardsView extends BudgetsCardsView {

    protected FavouriteBudgetsCardsView(FavouriteBudgetsCardsPresenter presenter) {
        super(presenter);
    }

    @Override
    protected Div initContent() {
        Div root = super.initContent();
        root.removeClassNames(
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.Grid.Breakpoint.Large.COLUMNS_3
        );
        root.addClassNames(
                LumoUtility.Grid.FLOW_COLUMN,
                LumoUtility.Grid.Row.ROWS_1,
                LumoUtility.Flex.SHRINK_NONE,
                LumoUtility.Overflow.AUTO
        );

        return root;
    }

    @Override
    protected String getEmptyStateText() {
        return "Здесь будут избранные бюджеты";
    }

    @Override
    protected boolean isAddBudgetButtonVisible() {
        return false;
    }

    @Override
    protected void configureBudgetCard(BudgetCard card, Budget budget) {
        card.addContentClickListener(event -> this.getPresenter().onEditRequested(budget));
        card.setMinWidth(18, Unit.REM);
        card.setMaxWidth(25, Unit.REM);
    }
}
