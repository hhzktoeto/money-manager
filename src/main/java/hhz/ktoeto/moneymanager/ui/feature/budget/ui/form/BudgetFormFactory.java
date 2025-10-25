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
                formLogic::addCategory,
                formLogic::submitCreate,
                formLogic::cancelCreate,
                formLogic::delete
        );
        createForm.showDeleteButton(false);
        formLogic.setForm(createForm);

        return createForm;
    }

    public BudgetForm budgetEditForm() {
        BudgetForm editForm = new BudgetForm(
                categoryDataProvider,
                formLogic::addCategory,
                formLogic::submitEdit,
                formLogic::cancelEdit,
                formLogic::delete
        );
        editForm.showDeleteButton(true);
        formLogic.setForm(editForm);

        return editForm;
    }
}
