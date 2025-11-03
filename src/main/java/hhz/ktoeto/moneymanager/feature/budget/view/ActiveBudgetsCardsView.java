package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

public class ActiveBudgetsCardsView extends BudgetsCardsView {

    public ActiveBudgetsCardsView(ActiveBudgetsCardPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет активных бюджетов";
    }

    @Override
    protected boolean isAddBudgetButtonVisible() {
        return true;
    }

    @Override
    protected void configureBudgetCard(BudgetCard card, Budget budget) {
        card.addContentClickListener(event -> this.getPresenter().onEditRequested(budget));
        card.addFavouriteButtonClickListener(event -> this.getPresenter().onAddToFavourites(budget));
    }
}
