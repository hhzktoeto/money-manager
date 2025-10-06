package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.category.CategoryNameDataProvider;
import hhz.ktoeto.moneymanager.ui.converter.CategoryNameToCategoryConverter;
import hhz.ktoeto.moneymanager.ui.converter.MathExpressionToBigDecimalConverter;
import hhz.ktoeto.moneymanager.ui.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.validator.TransactionDescriptionValidator;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionFormFactory {

    private final MathExpressionToBigDecimalConverter amountConverter;
    private final CategoryNameToCategoryConverter categoryConverter;
    private final TransactionAmountValidator amountValidator;
    private final TransactionDescriptionValidator descriptionValidator;
    private final CategoryNameDataProvider categoryNameDataProvider;

    private final TransactionCreateFormLogic createFormLogic;

    public TransactionForm transactionCreateForm() {
        return new TransactionForm(amountConverter, categoryConverter, categoryNameDataProvider, amountValidator, descriptionValidator, createFormLogic);
    }
}
