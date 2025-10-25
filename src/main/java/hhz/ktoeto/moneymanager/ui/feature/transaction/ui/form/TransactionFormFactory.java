package hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class TransactionFormFactory {

    private final CategoryDataProvider categoryDataProvider;

    private final TransactionFormLogic formLogic;

    public TransactionForm transactionCreateForm() {
        TransactionForm createForm = new TransactionForm(
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

    public TransactionForm transactionEditForm() {
        TransactionForm editForm = new TransactionForm(
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
