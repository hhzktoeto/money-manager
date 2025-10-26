package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;

public interface ActiveBudgetsViewPresenter extends ViewPresenter<ActiveBudgetsView> {

    void onCreateRequested();

    void onEditRequested(Budget budget);

    void init();
}
