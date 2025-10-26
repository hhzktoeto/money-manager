package hhz.ktoeto.moneymanager.feature.user.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class RegisterFormFactory {

    private final DefaultRegisterFormLogic defaultLogic;

    public RegisterForm defaultRegisterForm() {
        return new RegisterForm(defaultLogic);
    }
}
