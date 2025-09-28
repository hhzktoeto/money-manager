package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.constant.CookieConstant;
import hhz.ktoeto.moneymanager.constant.RouteName;
import hhz.ktoeto.moneymanager.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.form.RegisterForm;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.model.LoginRequest;
import hhz.ktoeto.moneymanager.user.model.RegisterRequest;
import hhz.ktoeto.moneymanager.user.service.UserService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@UIScope
@Component
@AnonymousAllowed
@Route(RouteName.LOGIN)
public class LoginView extends VerticalLayout {

    private final LoginForm loginForm = new LoginForm();
    private final RegisterForm registerForm = new RegisterForm();

    public LoginView(UserService userService) {
        loginForm.setVisible(true);
        registerForm.setVisible(false);
        registerForm.setVisible(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.getOpenRegisterButton().addClickListener(e -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        });
        registerForm.getOpenLoginButton().addClickListener(e -> {
            loginForm.setVisible(true);
            registerForm.setVisible(false);
        });

        loginForm.getLoginButton().addClickListener(event -> {
            try {
                AuthResponse response = userService.login(
                        new LoginRequest(loginForm.login(), loginForm.password())
                );

                VaadinService.getCurrentResponse().addCookie(buildCookie(CookieConstant.ACCESS_TOKEN, response.accessToken(), CookieConstant.ACCESS_TOKEN_MAX_AGE));
                VaadinService.getCurrentResponse().addCookie(buildCookie(CookieConstant.REFRESH_TOKEN, response.refreshToken(), CookieConstant.REFRESH_TOKEN_MAX_AGE));

                UI.getCurrent().getPage().setLocation(RouteName.MAIN);
            } catch (Exception e) {
                log.error("Exception occurred, while trying to login", e);
            }
        });
        registerForm.getRegisterButton().addClickListener(e -> {
            try {
                userService.register(new RegisterRequest(
                                registerForm.login(), registerForm.password(), registerForm.email(), registerForm.phone())
                );

                loginForm.getLoginField().setValue(registerForm.getLoginField().getValue());
                loginForm.getPasswordField().setValue(registerForm.getPasswordField().getValue());

                registerForm.setVisible(false);
                registerForm.clear();
                loginForm.setVisible(true);
            } catch (Exception ex) {
                log.error("Exception occurred, while trying to register", e);
            }
        });

        Card card = new Card();
        card.addClassName("login-card");
        card.add(new H1("Money Manager"), loginForm, registerForm);
        card.setWidth("27%");
        card.setMinWidth("27%");
        add(card);
    }

    private Cookie buildCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
