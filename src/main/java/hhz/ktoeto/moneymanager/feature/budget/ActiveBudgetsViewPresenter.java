package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.ui.InitializedManually;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;

public interface ActiveBudgetsViewPresenter extends ViewPresenter<ActiveBudgetsView>, InitializedManually {

    void onCreateRequested();

    void onEditRequested(Budget budget);
}
