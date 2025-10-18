package hhz.ktoeto.moneymanager.feature.budget.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.binder.Binder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BudgetForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<BudgetForm> categoryAddAction;
    private final transient Consumer<BudgetForm> submitAction;
    private final transient Consumer<BudgetForm> cancelAction;

    private final Binder<Budget> binder = new Binder<>(Budget.class);
}
