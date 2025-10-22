package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class BudgetFormFactory {

    private final CategoryDataProvider categoryDataProvider;
    private final BudgetFormLogic formLogic;

    public BudgetForm budgetCreateForm() {
        return new BudgetForm(
                categoryDataProvider,
                form -> formLogic.addCategory(),
                formLogic::submitCreate,
                form -> formLogic.cancelCreate()
        );
    }

    public BudgetForm budgetEditForm() {
        return new BudgetForm(
                categoryDataProvider,
                form -> formLogic.addCategory(),
                formLogic::submitEdit,
                form -> formLogic.cancelEdit()
        );
    }
}
