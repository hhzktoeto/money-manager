package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class LoginCard extends VerticalLayout {

    private boolean registerMode = false;

    @Getter
    @Setter
    public static class LoginData {
        private String login;
        private String password;
    }

    @Getter
    @Setter
    public static class RegisterData {
        private String login;
        private String password;
        private String email;
        private String phone;
    }

    private final Binder<LoginData> loginBinder = new Binder<>(LoginData.class);
    private final Binder<RegisterData> registerBinder = new Binder<>(RegisterData.class);

    private final Consumer<Void> onSuccess;

    public LoginCard(Runnable onSuccess) {
        this.onSuccess = v -> onSuccess.run();
        addClassName("card");
        setWidth("460px");
        setPadding(true);
        setAlignItems(Alignment.STRETCH);

        createHeader();
        showLoginForm();
    }

    private void createHeader() {
        H1 title = new H1();
        // стилизуем как в примере
        title.getElement().setText("Money Manager");
        title.addClassName("login-title");
        H2 sub = new H2("Вход");
        sub.addClassName("login-subtitle");
        add(title, sub);
    }

    private void showLoginForm() {
        registerMode = false;
        removeAll();
        createHeader();

        FormLayout form = new FormLayout();
        TextField login = new TextField();
        login.setLabel("Логин*");
        login.setId("login-input");

        PasswordField password = new PasswordField();
        password.setLabel("Пароль*");
        password.setId("password-input");

        loginBinder.forField(login).asRequired("Введите логин").bind(LoginData::getLogin, LoginData::setLogin);
        loginBinder.forField(password).asRequired("Введите пароль").bind(LoginData::getPassword, LoginData::setPassword);

        Button btnRegisterOpen = new Button("Зарегистрироваться", e -> showRegisterForm());
        Button btnLogin = new Button("Войти", e -> {
            LoginData data = new LoginData();
            loginBinder.writeBeanIfValid(data);
            // TODO: тут надо вызвать аутентификацию через Spring Security
            Notification.show("Login clicked: " + data.getLogin(), 1200, Notification.Position.BOTTOM_CENTER);
            // Для demo — считаем что логин успешен и вызываем callback:
            onSuccess.accept(null);
        });
        btnLogin.addClassName("primary");

        form.add(login, password);
        HorizontalLayout actions = new HorizontalLayout(btnRegisterOpen, btnLogin);
        actions.setWidthFull();
        actions.setJustifyContentMode(JustifyContentMode.BETWEEN);

        add(form, actions);
    }

    private void showRegisterForm() {
        registerMode = true;
        removeAll();
        createHeader();
        H2 sub = new H2("Регистрация");
        sub.addClassName("login-subtitle");
        add(sub);

        FormLayout form = new FormLayout();
        TextField login = new TextField();
        login.setLabel("Логин*");
        login.setId("register-login-input");

        PasswordField password = new PasswordField();
        password.setLabel("Пароль*");
        password.setId("register-password-input");

        EmailField email = new EmailField();
        email.setLabel("Почта");
        email.setId("register-email-input");

        TextField phone = new TextField();
        phone.setLabel("Телефон");
        phone.setId("register-phone-input");
        phone.setPlaceholder("+7 (123) 456-7890");

        registerBinder.forField(login).asRequired("Введите логин").bind(RegisterData::getLogin, RegisterData::setLogin);
        registerBinder.forField(password).asRequired("Введите пароль").bind(RegisterData::getPassword, RegisterData::setPassword);
        registerBinder.forField(email).bind(RegisterData::getEmail, RegisterData::setEmail);
        registerBinder.forField(phone).bind(RegisterData::getPhone, RegisterData::setPhone);

        Button btnHaveAccount = new Button("У меня есть аккаунт", e -> showLoginForm());
        Button btnRegister = new Button("Регистрация", e -> {
            RegisterData data = new RegisterData();
            if (registerBinder.writeBeanIfValid(data)) {
                // TODO: здесь регистрация пользователя через backend
                Notification.show("Регистрация: " + data.getLogin(), 1600, Notification.Position.BOTTOM_CENTER);
                // для demo — считаем, что регистрация прошла успешно и переключаемся на login
                showLoginForm();
            } else {
                Notification.show("Проверьте поля регистрации", 1200, Notification.Position.BOTTOM_CENTER);
            }
        });
        btnRegister.addClassName("primary");

        form.add(login, password, email, phone);
        HorizontalLayout actions = new HorizontalLayout(btnHaveAccount, btnRegister);
        actions.setWidthFull();
        actions.setJustifyContentMode(JustifyContentMode.BETWEEN);

        add(form, actions);
    }
}
