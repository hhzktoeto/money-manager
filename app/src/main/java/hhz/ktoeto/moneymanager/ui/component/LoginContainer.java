package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.component.container.BasicContainer;
import hhz.ktoeto.moneymanager.utils.RouteName;
import hhz.ktoeto.moneymanager.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.form.RegisterForm;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.model.LoginRequest;
import hhz.ktoeto.moneymanager.user.model.RegisterRequest;
import hhz.ktoeto.moneymanager.user.service.UserService;
import hhz.ktoeto.moneymanager.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class LoginContainer extends BasicContainer {

    public LoginContainer(UserService userService) {
        LoginForm loginForm = new LoginForm();
        RegisterForm registerForm = new RegisterForm();

        loginForm.setVisible(true);
        registerForm.setVisible(false);

        loginForm.addOpenRegisterButtonClickListener(e -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        });
        registerForm.addOpenLoginButtonClickListener(e -> {
            loginForm.setVisible(true);
            registerForm.setVisible(false);
        });

        loginForm.addLoginButtonClickListener(event -> {
            try {
                AuthResponse response = userService.login(
                        new LoginRequest(loginForm.login(), loginForm.password())
                );
                CookieUtils.addTokensToClientsCookies(response.accessToken(), response.refreshToken());

                UI.getCurrent().getPage().setLocation(RouteName.MAIN);
            } catch (Exception e) {
                log.error("Exception occurred, while trying to login", e);
            }
        });
        registerForm.addRegisterButtonClickListener(e -> {
            try {
                userService.register(new RegisterRequest(
                        registerForm.login(),
                        registerForm.password(),
                        registerForm.email().orElse(null),
                        registerForm.phone().orElse(null))
                );

                loginForm.setLoginValue(registerForm.login());
                loginForm.setPasswordValue(registerForm.password());

                registerForm.setVisible(false);
                registerForm.clear();
                loginForm.setVisible(true);
            } catch (Exception ex) {
                log.error("Exception occurred, while trying to register", ex);
            }
        });

        this.setHeader(new H1(new Span("M"), new Span("oney "), new Span("M"), new Span("anager")));
        this.addClassName("login-container");
        this.setContent(loginForm, registerForm);
    }
}
