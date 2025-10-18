package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BudgetForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<BudgetForm> categoryAddAction;
    private final transient Consumer<BudgetForm> submitAction;
    private final transient Consumer<BudgetForm> cancelAction;

    private final Binder<Budget> binder = new Binder<>(Budget.class);

    private IncomeExpenseToggle<Budget.Type> typeToggle;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        typeToggle = new IncomeExpenseToggle<>(Budget.Type.EXPENSE, Budget.Type.INCOME);
        root.add(typeToggle);

        return root;
    }
}
