package hhz.ktoeto.moneymanager.feature.login.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.login.domain.RegisterRequest;
import hhz.ktoeto.moneymanager.feature.login.ui.form.converter.EmailConverter;
import hhz.ktoeto.moneymanager.feature.login.ui.form.converter.PhoneConverter;
import hhz.ktoeto.moneymanager.feature.login.ui.form.validator.PasswordValidator;
import hhz.ktoeto.moneymanager.feature.login.ui.form.validator.PhoneValidator;
import hhz.ktoeto.moneymanager.feature.login.ui.form.validator.UsernameValidator;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

public class RegisterForm extends Composite<VerticalLayout> {

    private final transient RegisterFormLogic formLogic;

    private TextField loginField;
    private PasswordField passwordField;
    private EmailField emailField;
    private TextField phoneField;
    private Button submitButton;
    private Button loginButton;

    private Binder<RegisterRequest> binder;

    public RegisterForm(RegisterFormLogic formLogic) {
        this.formLogic = formLogic;
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();

        loginField = new TextField("Логин");
        loginField.setWidthFull();
        loginField.setPrefixComponent(MaterialIcons.PERSON.create());
        loginField.getPrefixComponent().addClassName(LumoUtility.FontSize.SMALL);
        root.add(loginField);

        passwordField = new PasswordField("Пароль");
        passwordField.setWidthFull();
        passwordField.setPrefixComponent(MaterialIcons.PASSWORD.create());
        passwordField.getPrefixComponent().addClassName(LumoUtility.FontSize.SMALL);
        root.add(passwordField);

        emailField = new EmailField("E-mail");
        emailField.setWidthFull();
        emailField.setPrefixComponent(MaterialIcons.MAIL.create());
        emailField.getPrefixComponent().addClassName(LumoUtility.FontSize.SMALL);
        emailField.setPlaceholder("e-mail@example.com");
        root.add(emailField);

        phoneField = new TextField("Телефон");
        phoneField.setWidthFull();
        phoneField.setPrefixComponent(MaterialIcons.PHONE.create());
        phoneField.getPrefixComponent().addClassName(LumoUtility.FontSize.SMALL);
        phoneField.setPlaceholder("+7");
        root.add(phoneField);

        submitButton = new Button("Зарегистрироваться");
        submitButton.setWidthFull();
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickShortcut(Key.ENTER);
        submitButton.addClickListener(ignored -> formLogic.onSubmit(this));
        root.add(submitButton);

        loginButton = new Button("Войти");
        loginButton.addClassName(LumoUtility.FontWeight.LIGHT);
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        loginButton.addClickListener(ignored -> formLogic.onLogin(this));

        Span hasAccountSpan = new Span("Уже есть аккаунт?");
        hasAccountSpan.addClassName(LumoUtility.FontWeight.EXTRALIGHT);
        hasAccountSpan.getStyle().set(StyleConstants.COLOR, StyleConstants.Color.PRIMARY_CONTRAST_40);
        HorizontalLayout hasAccountLayout = new HorizontalLayout(hasAccountSpan, loginButton);
        hasAccountLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.CENTER
        );
        root.add(hasAccountLayout);

        binder = new Binder<>(RegisterRequest.class);
        binder.forField(loginField)
                .asRequired("Не введён логин")
                .withValidator(new UsernameValidator())
                .bind(RegisterRequest::getLogin, RegisterRequest::setLogin);
        binder.forField(passwordField)
                .asRequired("Не введён пароль")
                .withValidator(new PasswordValidator())
                .bind(RegisterRequest::getPassword, RegisterRequest::setPassword);
        binder.forField(emailField)
                .withConverter(new EmailConverter())
                .withValidator(new EmailValidator("Некорректный e-mail адрес", true))
                .bind(RegisterRequest::getEmail, RegisterRequest::setEmail);
        binder.forField(phoneField)
                .withConverter(new PhoneConverter())
                .withValidator(new PhoneValidator())
                .bind(RegisterRequest::getPhone, RegisterRequest::setPhone);

        return root;
    }

    public void clear() {
        this.loginField.clear();
        this.passwordField.clear();
        this.emailField.clear();
        this.phoneField.clear();
    }

    boolean writeTo(RegisterRequest registerRequest) {
        return binder.writeBeanIfValid(registerRequest);
    }

    void setDisabled(boolean disabled) {
        submitButton.setEnabled(!disabled);
        loginButton.setEnabled(!disabled);
        loginField.setReadOnly(disabled);
        passwordField.setReadOnly(disabled);
        emailField.setReadOnly(disabled);
        phoneField.setReadOnly(disabled);
    }
}
