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

    public LoginForm() {
        this.loginButton.addClickShortcut(Key.ENTER);

        this.openRegisterButton.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("background", "transparent")
                .set("border", "none");
        this.loginButton.getStyle()
                .set("background", "var(--lumo-primary-color)")
                .set("color", "var(--lumo-primary-contrast-color)");

        HorizontalLayout buttonsLayout = new HorizontalLayout(openRegisterButton, loginButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setAlignItems(Alignment.CENTER);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        this.loginField.setWidthFull();
        this.passwordField.setWidthFull();

        setAlignItems(Alignment.STRETCH);
        setSpacing(true);
        setPadding(false);
        add(this.loginField, this.passwordField, buttonsLayout);
    }

    public String login() {
        return this.loginField.getValue();
    }

    public String password() {
        return this.passwordField.getValue();
    }
}
