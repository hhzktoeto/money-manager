package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;

public interface BudgetFormViewPresenter extends FormViewPresenter<Budget, BudgetFormView>, CanAddCategory {
}
