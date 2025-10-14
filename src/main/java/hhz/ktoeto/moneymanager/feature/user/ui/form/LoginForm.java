package hhz.ktoeto.moneymanager.feature.user.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.user.domain.LoginRequest;
import hhz.ktoeto.moneymanager.feature.user.ui.form.validator.PasswordValidator;
import hhz.ktoeto.moneymanager.feature.user.ui.form.validator.UsernameValidator;
import hhz.ktoeto.moneymanager.utils.StylingUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginForm extends Composite<VerticalLayout> {

    private final transient LoginFormLogic formLogic;

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
        passwordField.addKeyPressListener(Key.ENTER, ignored -> formLogic.onSubmit(this));
        root.add(passwordField);

        submitButton = new Button("Войти");
        submitButton.setWidthFull();
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickShortcut(Key.ENTER);
        submitButton.addClickListener(ignored -> formLogic.onSubmit(this));
        root.add(submitButton);

        registerButton = new Button("Зарегистрироваться");
        registerButton.addClassName(LumoUtility.FontWeight.LIGHT);
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        registerButton.addClickListener(ignored -> formLogic.onRegister(this));

        Span noAccountSpan = new Span("Нет аккаунта?");
        noAccountSpan.addClassName(LumoUtility.FontWeight.EXTRALIGHT);
        noAccountSpan.getStyle().set(StylingUtils.COLOR, StylingUtils.Color.PRIMARY_CONTRAST_40);
        HorizontalLayout noAccountLayout = new HorizontalLayout(noAccountSpan, registerButton);
        noAccountLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.CENTER
        );
        root.add(noAccountLayout);

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
                .bind(LoginRequest::getLogin, LoginRequest::setLogin);
        binder.forField(passwordField)
                .asRequired("Не введён пароль")
                .withValidator(new PasswordValidator())
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

    public void setLoginValue(String login) {
        this.loginField.setValue(login);
    }

    boolean writeTo(LoginRequest loginRequest) {
        return binder.writeBeanIfValid(loginRequest);
    }

    void setDisabled(boolean disabled) {
        submitButton.setEnabled(!disabled);
        registerButton.setEnabled(!disabled);
        loginField.setReadOnly(disabled);
        passwordField.setReadOnly(disabled);
    }

    Components components() {
        return new Components(springUsername, springPassword, springForm);
    }

    record Components(Input usernameInput, Input passwordInput, HtmlComponent hiddenForm) {}
}
