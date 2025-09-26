package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import org.springframework.stereotype.Component;

// View - наполнение страницы. В layout можно расположить разные View в разные места
@UIScope
@Component
@Route(value = "login", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView(LoginForm loginForm) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(loginForm);
    }
}
