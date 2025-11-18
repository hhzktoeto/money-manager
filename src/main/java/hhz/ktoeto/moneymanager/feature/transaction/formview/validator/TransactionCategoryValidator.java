package hhz.ktoeto.moneymanager.feature.transaction.formview.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;

public class TransactionCategoryValidator implements Validator<Category> {

    private static final String NO_VALUE_ERROR_MESSAGE = "Не выбрана категория";

    @Override
    public ValidationResult apply(Category category, ValueContext valueContext) {
        if (category == null) {
            return ValidationResult.error(NO_VALUE_ERROR_MESSAGE);
        }
        return ValidationResult.ok();
    }
}
