package hhz.ktoeto.moneymanager.feature.user;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.feature.user.ui.LoginCard;

@UIScope
@SpringComponent
@AnonymousAllowed
@Route(Routes.Path.LOGIN)
public class LoginRouteView extends VerticalLayout {

    public LoginRouteView(LoginCard loginContainer) {
        this.setSpacing(false);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER
        );
        this.add(loginContainer);
    }
}
