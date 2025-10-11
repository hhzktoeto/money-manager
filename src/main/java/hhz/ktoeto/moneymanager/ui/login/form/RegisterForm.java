package hhz.ktoeto.moneymanager.ui.login.form;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.utils.StylingUtils;

import java.util.Optional;

//TODO: убрать логику кнопок отсюда, сделать интерфейс
public class RegisterForm extends Composite<VerticalLayout> {

    private TextField loginField;
    private PasswordField passwordField;
    private EmailField emailField;
    private TextField phoneField;
    private Button submitButton;
    private Button loginButton;

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();

        loginField = new TextField("Логин");
        loginField.setWidthFull();
        loginField.setPrefixComponent(VaadinIcon.USER.create());
        root.add(loginField);

        passwordField = new PasswordField("Пароль");
        passwordField.setWidthFull();
        passwordField.setPrefixComponent(VaadinIcon.KEY.create());
        root.add(passwordField);

        emailField = new EmailField("E-mail");
        emailField.setWidthFull();
        emailField.setPrefixComponent(VaadinIcon.AT.create());
        emailField.setPlaceholder("e-mail@example.com");
        root.add(emailField);

        phoneField = new TextField("Телефон");
        phoneField.setWidthFull();
        phoneField.setPrefixComponent(VaadinIcon.PHONE.create());
        phoneField.setPlaceholder("+7");
        root.add(phoneField);

        submitButton = new Button("Зарегистрироваться");
        submitButton.setWidthFull();
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickShortcut(Key.ENTER);
        root.add(submitButton);

        loginButton = new Button("Войти");
        loginButton.addClassName(LumoUtility.FontWeight.LIGHT);
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        Span hasAccountSpan = new Span("Уже есть аккаунт?");
        hasAccountSpan.addClassName(LumoUtility.FontWeight.EXTRALIGHT);
        hasAccountSpan.getStyle().set(StylingUtils.COLOR, StylingUtils.Color.PRIMARY_CONTRAST_40);
        HorizontalLayout hasAccountLayout = new HorizontalLayout(hasAccountSpan, loginButton);
        hasAccountLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.CENTER
        );
        root.add(hasAccountLayout);

        return root;
    }

    public void clear() {
        this.loginField.clear();
        this.passwordField.clear();
        this.emailField.clear();
        this.phoneField.clear();
    }

    public void onRegisterButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.submitButton.addClickListener(event);
    }

    public void onOpenLoginButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.loginButton.addClickListener(event);
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
}
