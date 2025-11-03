package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

public class ExpiredBudgetsCardsView extends BudgetsCardsView {

    public ExpiredBudgetsCardsView(ExpiredBudgetsCardsPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет бюджетов с истёкшим сроком действия";
    }

    @Override
    protected boolean isAddBudgetButtonVisible() {
        return false;
    }

    @Override
    protected void configureBudgetCard(BudgetCard card, Budget budget) {
        card.addContentClickListener(event -> this.getPresenter().onEditRequested(budget));
        card.hideAddToFavouriteButton();
    }
}
