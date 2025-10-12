package hhz.ktoeto.moneymanager.ui.login.validator;

import com.vaadin.flow.data.validator.RegexpValidator;

public class PhoneValidator extends RegexpValidator {

    public PhoneValidator() {
        super("Некорректный номер телефона", "^(\\+7|8)\\d{10}$");
    }
}
