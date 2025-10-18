package hhz.ktoeto.moneymanager.ui.feature.budget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.OpenBudgetCreateDialogEvent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.BUDGET, layout = MainLayout.class)
public class BudgetView extends VerticalLayout {

    public BudgetView(ApplicationEventPublisher eventPublisher) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );
        Button button = new Button("Budget");

        button.addClickListener(event -> eventPublisher.publishEvent(new OpenBudgetCreateDialogEvent(this)));

        add(button);
    }
}
