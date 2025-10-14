package hhz.ktoeto.moneymanager.feature.user.ui.form;

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
