package hhz.ktoeto.moneymanager.ui.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class LoginForm extends VerticalLayout {

    private final TextField loginField = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");
    private final Button openRegisterButton = new Button("Зарегистрироваться");
    private final Button loginButton = new Button("Войти");

    private final HorizontalLayout buttonsLayout = new HorizontalLayout(openRegisterButton, loginButton);

    public LoginForm() {
        this.loginButton.addClickShortcut(Key.ENTER);

        this.buttonsLayout.setAlignItems(Alignment.CENTER);
        this.buttonsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        setAlignItems(Alignment.STRETCH);
        add(this.loginField, this.passwordField, this.buttonsLayout);
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }
}
