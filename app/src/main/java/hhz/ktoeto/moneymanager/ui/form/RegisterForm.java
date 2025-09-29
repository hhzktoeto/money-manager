package hhz.ktoeto.moneymanager.ui.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class RegisterForm extends VerticalLayout {

    private final TextField loginField = new TextField("Логин");
    private final TextField passwordField = new TextField("Пароль");
    private final EmailField emailField = new EmailField("E-mail");
    private final TextField phoneField = new TextField("Телефон");
    private final Button openLoginButton = new Button("У меня есть аккаунт");
    private final Button registerButton = new Button("Регистрация");

    public RegisterForm() {
        this.registerButton.addClickShortcut(Key.ENTER);

        this.openLoginButton.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("background", "transparent")
                .set("border", "none");

        this.registerButton.getStyle()
                .set("background", "var(--lumo-primary-color)")
                .set("color", "var(--lumo-primary-contrast-color)");

        HorizontalLayout buttonsLayout = new HorizontalLayout(openLoginButton, registerButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setAlignItems(Alignment.CENTER);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        buttonsLayout.setAlignItems(Alignment.CENTER);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        setAlignItems(Alignment.STRETCH);
        setSpacing(true);
        setPadding(false);
        add(
                this.loginField,
                this.passwordField,
                this.emailField,
                this.phoneField,
                buttonsLayout
        );
    }

    public void clear() {
        this.loginField.clear();
        this.passwordField.clear();
        this.emailField.clear();
        this.phoneField.clear();
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }

    public String email() {
        return this.emailField.getValue();
    }

    public String phone() {
        return this.phoneField.getValue();
    }
}
