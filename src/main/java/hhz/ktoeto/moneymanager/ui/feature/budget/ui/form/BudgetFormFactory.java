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
        BudgetForm createForm = new BudgetForm(
                categoryDataProvider,
                form -> formLogic.addCategory(),
                formLogic::submitCreate,
                form -> formLogic.cancelCreate(),
                formLogic::delete
        );
        createForm.showDeleteButton(false);

        return createForm;
    }

    public BudgetForm budgetEditForm() {
        BudgetForm editForm = new BudgetForm(
                categoryDataProvider,
                form -> formLogic.addCategory(),
                formLogic::submitEdit,
                form -> formLogic.cancelEdit(),
                formLogic::delete
        );
        editForm.showDeleteButton(true);

        return editForm;
    }
}
