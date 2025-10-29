package hhz.ktoeto.moneymanager.feature.budget;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;

public interface BudgetFormViewPresenter extends FormViewPresenter<Budget, BudgetFormView>, CanAddCategory {
}
