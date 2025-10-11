package hhz.ktoeto.moneymanager.ui.login.form;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.login.validator.UsernameValidator;
import hhz.ktoeto.moneymanager.utils.StylingUtils;
import lombok.Getter;
import lombok.Setter;

public class LoginForm extends Composite<VerticalLayout> {

    private TextField loginField;
    private PasswordField passwordField;
    private Button submitButton;
    private Button registerButton;

    private HtmlComponent springForm;
    private Input springUsername;
    private Input springPassword;
    private VerticalLayout errorBox;

    private Binder<LoginRequest> binder;

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();

        errorBox = new VerticalLayout();
        errorBox.setVisible(false);
        errorBox.addClassNames(
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Gap.XSMALL,
                LumoUtility.Background.ERROR_10,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.TextColor.ERROR
        );
        root.add(errorBox);

        loginField = new TextField("Логин");
        loginField.setWidthFull();
        loginField.setPrefixComponent(VaadinIcon.USER.create());
        root.add(loginField);

        passwordField = new PasswordField("Пароль");
        passwordField.setWidthFull();
        passwordField.setPrefixComponent(VaadinIcon.KEY.create());
        passwordField.addKeyPressListener(Key.ENTER, ignored -> handleSubmit());
        root.add(passwordField);

        submitButton = new Button("Войти");
        submitButton.setWidthFull();
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickShortcut(Key.ENTER);
        submitButton.addClickListener(ignored -> handleSubmit());
        root.add(submitButton);

        registerButton = new Button("Зарегистрироваться");
        registerButton.addClassName(LumoUtility.FontWeight.LIGHT);
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        Span span = new Span("Нет аккаунта?");
        span.addClassName(LumoUtility.FontWeight.EXTRALIGHT);
        span.getStyle().set(StylingUtils.COLOR, StylingUtils.Color.PRIMARY_CONTRAST_40);
        HorizontalLayout buttonsLayout = new HorizontalLayout(span, registerButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        root.add(buttonsLayout);

        springUsername = new Input();
        springUsername.setType("hidden");
        springUsername.getElement().setAttribute("name", "username");

        springPassword = new Input();
        springPassword.setType("hidden");
        springPassword.getElement().setAttribute("name", "password");

        springForm = new HtmlComponent("form");
        springForm.getElement().setAttribute("method", "post");
        springForm.getElement().setAttribute("action", "/login");
        springForm.getElement().appendChild(springUsername.getElement(), springPassword.getElement());
        root.add(springForm);

        binder = new Binder<>(LoginRequest.class);
        binder.forField(loginField)
                .asRequired("Не введён логин")
                .withValidator(new UsernameValidator())
                .bind(LoginRequest::getUsername, LoginRequest::setUsername);
        binder.forField(passwordField)
                .asRequired("Не введён пароль")
                .bind(LoginRequest::getPassword, LoginRequest::setPassword);

        return root;
    }

    public void showErrorMessage(String title, String message) {
        errorBox.removeAll();
        Span head = new Span(title);
        head.addClassName(LumoUtility.FontWeight.BOLD);
        Span msg = new Span(message);
        errorBox.add(head, msg);
        errorBox.setVisible(true);
    }

    public void onOpenRegisterButtonClicked(ComponentEventListener<ClickEvent<Button>> event) {
        this.registerButton.addClickListener(event);
    }

    public void setLoginValue(String login) {
        this.loginField.setValue(login);
    }

    public void setPasswordValue(String password) {
        this.passwordField.setValue(password);
    }

    private void handleSubmit() {
        LoginRequest loginRequest = new LoginRequest();
        boolean valid = binder.writeBeanIfValid(loginRequest);
        if (!valid) {
            return;
        }

        disableWhileSubmitting(true);
        springUsername.setValue(loginRequest.getUsername());
        springPassword.setValue(loginRequest.getPassword());

        System.out.println("Username: " + loginRequest.getUsername() + ", password: " + loginRequest.getPassword());

        springForm.getElement().callJsFunction("submit");

        disableWhileSubmitting(false);
    }

    private void disableWhileSubmitting(boolean disable) {
        submitButton.setEnabled(!disable);
        registerButton.setEnabled(!disable);
        loginField.setReadOnly(disable);
        passwordField.setReadOnly(disable);
    }

    @Getter
    @Setter
    private static final class LoginRequest {
        private String username;
        private String password;
    }
}
