package hhz.ktoeto.moneymanager.ui.feature.budget;

import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

import java.util.List;

public interface ActiveBudgetsView extends View {

    void updateCards(List<BudgetCard> cardList);
}
