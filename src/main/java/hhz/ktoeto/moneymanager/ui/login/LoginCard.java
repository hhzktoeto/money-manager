package hhz.ktoeto.moneymanager.ui.login;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.backend.dto.RegisterRequest;
import hhz.ktoeto.moneymanager.backend.service.UserService;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.login.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.login.form.RegisterForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class LoginCard extends Composite<BasicContainer> implements BeforeEnterObserver {

    private final UserService userService;

    private LoginForm loginForm;
    private RegisterForm registerForm;

    public LoginCard(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();
        root.setMinWidth(320, Unit.PIXELS);
        root.setMaxWidth(400, Unit.PIXELS);
        root.addClassNamesToHeader(LumoUtility.JustifyContent.CENTER);

        Image appLogo = new Image("logo.png", "Money Manager");
        appLogo.setWidth(12, Unit.REM);
        root.setHeader(appLogo);

        loginForm = new LoginForm();
        registerForm = new RegisterForm();

        loginForm.setVisible(true);
        loginForm.onOpenRegisterButtonClicked(e -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        });

        registerForm.setVisible(false);
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
        root.setContent(loginForm, registerForm);

        return root;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.showErrorMessage("Неверный логин или пароль", "Попробуйте ещё раз");
        }
    }
}
