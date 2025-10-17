package hhz.ktoeto.moneymanager.feature.goals;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.core.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.GOALS, layout = MainLayout.class)
public class GoalsView extends VerticalLayout {

    public GoalsView() {
        setSizeFull();
    }
}
