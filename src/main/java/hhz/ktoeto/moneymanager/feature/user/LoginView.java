package hhz.ktoeto.moneymanager.feature.user;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.user.ui.LoginCard;
import hhz.ktoeto.moneymanager.utils.RouterUtils;

@UIScope
@SpringComponent
@AnonymousAllowed
@Route(RouterUtils.RouteName.LOGIN)
public class LoginView extends VerticalLayout {

    public LoginView(LoginCard loginContainer) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(loginContainer);
    }
}
