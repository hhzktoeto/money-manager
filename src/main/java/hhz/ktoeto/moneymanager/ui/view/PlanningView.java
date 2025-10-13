package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = RouterUtils.RouteName.PLANNING, layout = MainLayout.class)
public class PlanningView extends VerticalLayout {

    public PlanningView() {
        setSizeFull();
    }
}
