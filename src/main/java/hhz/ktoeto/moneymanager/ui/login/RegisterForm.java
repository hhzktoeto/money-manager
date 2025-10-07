package hhz.ktoeto.moneymanager.ui.login;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Optional;

public class RegisterForm extends VerticalLayout {

    private final TextField loginField = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");
    private final EmailField emailField = new EmailField("E-mail");
    private final TextField phoneField = new TextField("Телефон");
    private final Button openLoginButton = new Button("У меня есть аккаунт");
    private final Button registerButton = new Button("Регистрация");

    private final HorizontalLayout buttonsLayout = new HorizontalLayout(openLoginButton, registerButton);

    public RegisterForm() {
        this.registerButton.addClickShortcut(Key.ENTER);
        this.emailField.setPlaceholder("e-mail@example.com");
        this.phoneField.setPlaceholder("+7");

        this.applyStyling();

        this.add(this.loginField, this.passwordField, this.emailField, this.phoneField, buttonsLayout);
    }

    public void clear() {
        this.loginField.clear();
        this.passwordField.clear();
        this.emailField.clear();
        this.phoneField.clear();
    }

    public void onRegisterButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.registerButton.addClickListener(event);
    }

    public void onOpenLoginButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.openLoginButton.addClickListener(event);
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }

    public Optional<String> email() {
        return this.emailField.getOptionalValue();
    }

    public Optional<String> phone() {
        return this.phoneField.getOptionalValue();
    }

    private void applyStyling() {
        this.openLoginButton.addClassName("open-login-button");
        this.registerButton.addClassName("register-button");
        this.buttonsLayout.addClassName("buttons-layout");
        this.loginField.addClassName("login-field");
        this.passwordField.addClassName("password-field");
        this.emailField.addClassName("email-field");
        this.phoneField.addClassName("phone-field");
        this.addClassName("register-form");
    }
}
