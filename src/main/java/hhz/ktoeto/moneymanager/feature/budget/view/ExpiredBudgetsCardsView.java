package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;
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
        card.hideProgressSpan();
        String formattedStartDate = this.getPresenter().formatDate(budget.getStartDate());
        String formattedEndDate = this.getPresenter().formatDate(budget.getEndDate());
        Span activityDatesSpan = new Span(formattedStartDate + " - " + formattedEndDate);
        activityDatesSpan.addClassNames(
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.FontSize.SMALL,
                LumoUtility.FontWeight.LIGHT,
                LumoUtility.TextColor.DISABLED
        );

        card.addToContentArea(activityDatesSpan);
    }
}
