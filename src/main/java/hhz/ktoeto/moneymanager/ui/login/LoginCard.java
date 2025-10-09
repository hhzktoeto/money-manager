package hhz.ktoeto.moneymanager.ui.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.backend.dto.RegisterRequest;
import hhz.ktoeto.moneymanager.backend.service.UserService;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.login.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.login.form.RegisterForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class LoginCard extends BasicContainer implements BeforeEnterObserver {

    private final LoginForm loginForm;
    private final RegisterForm registerForm;

    public LoginCard(UserService userService) {
        this.loginForm = new LoginForm();
        this.registerForm = new RegisterForm();

        loginForm.setVisible(true);
        registerForm.setVisible(false);

        loginForm.onOpenRegisterButtonClicked(e -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        });

        registerForm.onOpenLoginButtonClicked(e -> {
            loginForm.setVisible(true);
            registerForm.setVisible(false);
        });
        registerForm.onRegisterButtonClicked(e -> {
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
        this.addClassName("login-card");
        this.setContent(loginForm, registerForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.showErrorMessage("Неверный логин или пароль", "Попробуйте ещё раз");
        }
    }
}
