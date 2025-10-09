package hhz.ktoeto.moneymanager.ui.login.form;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class LoginForm extends VerticalLayout {

    private final TextField loginField = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");

    private final Button openRegisterButton = new Button("Зарегистрироваться");
    private final Button loginButton = new Button("Войти");

    private final HorizontalLayout buttonsLayout = new HorizontalLayout(openRegisterButton, loginButton);

    private final HtmlComponent form = new HtmlComponent("form");
    private final Input hiddenUsername = new Input();
    private final Input hiddenPassword = new Input();

    private final Div errorBox = new Div(new Span());

    public LoginForm() {
        form.getElement().setAttribute("method", "post");
        form.getElement().setAttribute("action", "/login");
        hiddenUsername.setType("hidden");
        hiddenUsername.getElement().setAttribute("name", "username");
        hiddenPassword.setType("hidden");
        hiddenPassword.getElement().setAttribute("name", "password");
        form.getElement().appendChild(hiddenUsername.getElement(), hiddenPassword.getElement());

        this.add(form);

        this.loginButton.addClickShortcut(Key.ENTER);

        this.applyStyling();

        errorBox.setVisible(false);

        this.add(errorBox, this.loginField, this.passwordField, buttonsLayout);

        loginButton.addClickListener(this::handleSubmit);
        passwordField.addKeyPressListener(Key.ENTER, e -> handleSubmit(null));
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }

    public void showErrorMessage(String title, String message) {
        errorBox.removeAll();
        Div h = new Div(new Span(title));
        h.setClassName("error-title");
        Div d = new Div(new Span(message));
        d.setClassName("error-message");
        errorBox.add(h, d);
        errorBox.setVisible(true);
    }

    public void onOpenRegisterButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.openRegisterButton.addClickListener(event);
    }

    public void setLoginValue(String login) {
        this.loginField.setValue(login);
    }

    public void setPasswordValue(String password) {
        this.passwordField.setValue(password);
    }

    private void applyStyling() {
        this.addClassName("login-form");

        this.openRegisterButton.addClassName("open-register-button");
        this.loginButton.addClassName("login-button");
        this.buttonsLayout.addClassName("buttons-layout");
        this.errorBox.addClassName("error");
    }

    private void handleSubmit(ClickEvent<Button> e) {
        if (loginField.isEmpty() || passwordField.isEmpty()) {
            this.showErrorMessage("Ошибка", "Введите логин и пароль.");
            return;
        }

        disableWhileSubmitting(true);
        hiddenUsername.setValue(loginField.getValue());
        hiddenPassword.setValue(passwordField.getValue());

        form.getElement().callJsFunction("submit");

        disableWhileSubmitting(false);
    }

    private void disableWhileSubmitting(boolean disable) {
        loginButton.setEnabled(!disable);
        openRegisterButton.setEnabled(!disable);
        loginField.setReadOnly(disable);
        passwordField.setReadOnly(disable);
    }
}
