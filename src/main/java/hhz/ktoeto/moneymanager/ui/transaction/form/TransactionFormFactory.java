package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.ui.category.CategoryDataProvider;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class TransactionFormFactory {

    private final CategoryDataProvider categoryDataProvider;

    private final TransactionFormLogic transactionCreateLogic;
    private final TransactionFormLogic transactionEditLogic;

    public TransactionForm transactionCreateForm() {
        return new TransactionForm(categoryDataProvider, transactionCreateLogic);
    }

    public TransactionForm transactionEditForm() {
        return new TransactionForm(categoryDataProvider, transactionEditLogic);
    }
}
