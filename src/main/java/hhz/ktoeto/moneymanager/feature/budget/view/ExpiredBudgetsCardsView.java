package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.html.Div;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

import java.util.List;

public class ExpiredBudgetsCardsView extends BudgetsCardsView {

    public ExpiredBudgetsCardsView(BudgetsCardsPresenter presenter) {
        super(presenter);
    }

    @Override
    public void update(List<BudgetCard> data) {
        Div root = this.getContent();
        root.removeAll();

        data.forEach(card -> {
            card.hideAddToFavouriteButton();
            card.addContentClickListener(event -> this.presenter.onEditRequested(card.getBudget()));
            root.add(card);
        });
    }
}
