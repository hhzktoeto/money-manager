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

    private final TransactionCreateFormLogic createFormLogic;

    public TransactionForm transactionCreateForm() {
        return new TransactionForm(categoryDataProvider, createFormLogic);
    }
}
