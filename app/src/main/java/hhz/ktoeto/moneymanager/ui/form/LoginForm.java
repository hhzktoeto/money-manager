package hhz.ktoeto.moneymanager.ui.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@UIScope
@Component
public class LoginForm extends FormLayout {

    private final TextField login;
    private final TextField password;
    private final EmailField email;
    private final TextField phone;

    private final Button openRegisterButton;
    private final Button openLoginButton;
    private final Button enterButton;
    private final Button registerButton;

    public LoginForm() {
        this.login = new TextField("Логин");
        this.password = new TextField("Пароль");
        this.email = new EmailField("E-mail");
        this.phone = new TextField("Телефон");
        this.openRegisterButton = new Button("Зарегистрироваться");
        this.openLoginButton = new Button("У меня есть аккаунт");
        this.enterButton = new Button("Войти");
        this.registerButton = new Button("Зарегистрироваться");

        this.login.setId("login-input");
        this.password.setId("password-input");
        this.email.setId("email-input");
        this.phone.setId("phone-input");
        this.openRegisterButton.setId("open-register-button");
        this.openLoginButton.setId("open-login-button");
        this.enterButton.setId("enter-button");
        this.registerButton.setId("register-button");

        add(
                this.login,
                this.password,
                this.email,
                this.phone,
                this.openRegisterButton,
                this.openLoginButton,
                this.enterButton,
                this.registerButton
        );
    }
}
