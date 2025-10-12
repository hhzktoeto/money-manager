package hhz.ktoeto.moneymanager.ui.login.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.RequiredArgsConstructor;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class LoginFormFactory {

    private final DefaultLoginFormLogic defaultLogic;

    public LoginForm defaultLoginForm() {
        return new LoginForm(defaultLogic);
    }
}
