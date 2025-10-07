package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.backend.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.category.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.transaction.converter.MathExpressionToBigDecimalConverter;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionDescriptionValidator;
import lombok.RequiredArgsConstructor;

@SpringComponent
@RequiredArgsConstructor
public class TransactionFormFactory {

    private final MathExpressionToBigDecimalConverter amountConverter;
    private final TransactionAmountValidator amountValidator;
    private final TransactionDescriptionValidator descriptionValidator;
    private final CategoryDataProvider categoryDataProvider;

    private final TransactionCreateFormLogic createFormLogic;

    public TransactionForm transactionCreateForm() {
        return new TransactionForm(amountConverter, categoryDataProvider, amountValidator, descriptionValidator, createFormLogic);
    }
}
