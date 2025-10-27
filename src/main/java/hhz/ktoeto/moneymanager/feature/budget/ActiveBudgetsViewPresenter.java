package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.CanCreate;
import hhz.ktoeto.moneymanager.ui.CanEdit;
import hhz.ktoeto.moneymanager.ui.InitializedManually;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;

public interface ActiveBudgetsViewPresenter extends ViewPresenter<ActiveBudgetsView>, InitializedManually, CanCreate, CanEdit<Budget> {
}
