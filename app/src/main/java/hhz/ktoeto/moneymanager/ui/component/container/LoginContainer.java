package hhz.ktoeto.moneymanager.ui.component.container;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.constant.CookieConstant;
import hhz.ktoeto.moneymanager.constant.RouteName;
import hhz.ktoeto.moneymanager.ui.component.ComponentContainer;
import hhz.ktoeto.moneymanager.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.form.RegisterForm;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.model.LoginRequest;
import hhz.ktoeto.moneymanager.user.model.RegisterRequest;
import hhz.ktoeto.moneymanager.user.service.UserService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class LoginContainer extends ComponentContainer {

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

                VaadinService.getCurrentResponse().addCookie(buildCookie(CookieConstant.ACCESS_TOKEN, response.accessToken(), CookieConstant.ACCESS_TOKEN_MAX_AGE));
                VaadinService.getCurrentResponse().addCookie(buildCookie(CookieConstant.REFRESH_TOKEN, response.refreshToken(), CookieConstant.REFRESH_TOKEN_MAX_AGE));

                UI.getCurrent().getPage().setLocation(RouteName.MAIN);
            } catch (Exception e) {
                log.error("Exception occurred, while trying to login", e);
            }
        });
        registerForm.addRegisterButtonClickListener(e -> {
            try {
                userService.register(new RegisterRequest(
                        registerForm.login(), registerForm.password(), registerForm.email(), registerForm.phone())
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

        addClassName("login-card");
        addContentClassName("login-card-content");
        setHeader(new H1(new Span("M"), new Span("oney "), new Span("M"), new Span("anager")));
        setContent(loginForm, registerForm);
    }

    private Cookie buildCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
