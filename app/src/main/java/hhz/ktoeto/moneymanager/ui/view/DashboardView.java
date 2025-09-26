package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.form.LoginForm;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Route("")
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    public DashboardView(LoginForm loginForm) {
        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!SecurityUtils.isUserLoggedIn()) {
            beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }
}
