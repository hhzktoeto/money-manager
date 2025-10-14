package hhz.ktoeto.moneymanager.feature.budget;

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
@Route(value = Routes.Path.BUDGET, layout = MainLayout.class)
public class BudgetView extends VerticalLayout {

    public BudgetView() {
        setSizeFull();
    }
}
