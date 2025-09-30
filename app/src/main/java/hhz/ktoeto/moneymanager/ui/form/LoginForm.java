package hhz.ktoeto.moneymanager.ui.form;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class LoginForm extends VerticalLayout {

    private final TextField loginField = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");

    private final Button openRegisterButton = new Button("Зарегистрироваться");
    private final Button loginButton = new Button("Войти");

    public LoginForm() {
        this.loginButton.addClickShortcut(Key.ENTER);

        this.openRegisterButton.addClassName("open-register-button");
        this.loginButton.addClassName("login-button");

        HorizontalLayout buttonsLayout = new HorizontalLayout(openRegisterButton, loginButton);
        buttonsLayout.addClassName("buttons-layout");

        this.loginField.addClassName("login-field");
        this.passwordField.addClassName("password-field");

        this.addClassName("login-form");
        this.add(this.loginField, this.passwordField, buttonsLayout);
    }

    public void addLoginButtonClickListener(ComponentEventListener<ClickEvent<Button>> event) {
        loginButton.addClickListener(event);
    }

    public void addOpenRegisterButtonClickListener(ComponentEventListener<ClickEvent<Button>> event) {
        openRegisterButton.addClickListener(event);
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }

    public void setLoginValue(String login) {
        this.loginField.setValue(login);
    }

    public void setPasswordValue(String password) {
        this.passwordField.setValue(password);
    }
}
