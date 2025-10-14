package hhz.ktoeto.moneymanager.feature.transaction.ui.form.converter;

import com.udojava.evalex.Expression;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.math.BigDecimal;

public class MathExpressionToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public Result<BigDecimal> convertToModel(String expression, ValueContext context) {
        try {
            BigDecimal value = new Expression(expression).eval();
            return Result.ok(value);
        } catch (Exception e) {
            return Result.error("Некорректно введена сумма");
        }
    }

    @Override
    public String convertToPresentation(BigDecimal value, ValueContext context) {
        return value == null ? "" : value.toString();
    }
}
