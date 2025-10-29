package hhz.ktoeto.moneymanager.feature.login.ui.form.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class PhoneConverter implements Converter<String, String> {

    @Override
    public Result<String> convertToModel(String value, ValueContext context) {
        return value == null || value.isBlank() ? Result.ok(null) : Result.ok(value);
    }

    @Override
    public String convertToPresentation(String value, ValueContext context) {
        return value;
    }
}
