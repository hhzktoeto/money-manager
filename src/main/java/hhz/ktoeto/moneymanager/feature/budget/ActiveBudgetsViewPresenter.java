package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.mixin.CanCreate;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;

public interface ActiveBudgetsViewPresenter extends ViewPresenter<ActiveBudgetsView>, CanCreate, CanEdit<Budget> {

    void onAddToFavourite(Budget budget);
}
