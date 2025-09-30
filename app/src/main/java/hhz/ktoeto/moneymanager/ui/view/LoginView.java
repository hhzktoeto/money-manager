package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.constant.RouteName;
import hhz.ktoeto.moneymanager.ui.component.container.LoginContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@AnonymousAllowed
@Route(RouteName.LOGIN)
public class LoginView extends VerticalLayout {

    public LoginView(LoginContainer loginContainer) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginContainer.setHeader(new H1(new Span("M"), new Span("oney "), new Span("M"), new Span("anager")));
        loginContainer.addClassName("login-container");
        loginContainer.addContentClassName("login-container-content");

        add(loginContainer);
    }
}
