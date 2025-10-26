package hhz.ktoeto.moneymanager.feature.user.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.core.event.OpenLoginFormEvent;
import hhz.ktoeto.moneymanager.core.event.OpenRegisterFormEvent;
import hhz.ktoeto.moneymanager.core.event.UserRegisteredEvent;
import hhz.ktoeto.moneymanager.feature.user.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.feature.user.ui.form.LoginFormFactory;
import hhz.ktoeto.moneymanager.feature.user.ui.form.RegisterForm;
import hhz.ktoeto.moneymanager.feature.user.ui.form.RegisterFormFactory;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class LoginCard extends Composite<BasicContainer> implements BeforeEnterObserver {

    private final transient LoginFormFactory loginFormFactory;
    private final transient RegisterFormFactory registerFormFactory;

    private LoginForm loginForm;
    private RegisterForm registerForm;

    public LoginCard(LoginFormFactory loginFormFactory,
                     RegisterFormFactory registerFormFactory) {
        this.loginFormFactory = loginFormFactory;
        this.registerFormFactory = registerFormFactory;
    }

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();
        root.setMinWidth(320, Unit.PIXELS);
        root.setMaxWidth(400, Unit.PIXELS);

        FlexLayout header = root.getHeader();
        this.configureHeader(header);

        loginForm = loginFormFactory.defaultLoginForm();
        registerForm = registerFormFactory.defaultRegisterForm();

        loginForm.setVisible(true);
        registerForm.setVisible(false);

        root.setContent(loginForm, registerForm);

        return root;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.showErrorMessage("Неверный логин или пароль", "Попробуйте ещё раз");
        }
    }

    @EventListener(OpenRegisterFormEvent.class)
    private void onOpenRegisterFormEvent() {
        loginForm.setVisible(false);
        registerForm.setVisible(true);
    }

    @EventListener(OpenLoginFormEvent.class)
    private void onOpenLoginFormEvent() {
        loginForm.setVisible(true);
        registerForm.setVisible(false);
    }

    @EventListener(UserRegisteredEvent.class)
    private void onUserRegisteredEvent(UserRegisteredEvent event) {
        loginForm.setLoginValue(event.getUser().getLogin());

        registerForm.setVisible(false);
        registerForm.clear();
        loginForm.setVisible(true);
    }

    private void configureHeader(FlexLayout header) {
        header.addClassNames(LumoUtility.JustifyContent.CENTER);

        Image appLogo = new Image("logo.png", "Money Manager");
        appLogo.setWidth(12, Unit.REM);
        header.add(appLogo);
    }
}
