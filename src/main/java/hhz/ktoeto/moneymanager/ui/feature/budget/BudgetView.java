package hhz.ktoeto.moneymanager.ui.feature.budget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.OpenBudgetCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.ActiveBudgets;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.ApplicationEventPublisher;


@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.BUDGET, layout = MainLayout.class)
public class BudgetView extends VerticalLayout {

    public BudgetView(ApplicationEventPublisher eventPublisher, ActiveBudgets activeBudgetsCards) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );

        FlexLayout content = new FlexLayout();
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE
        );


        Button createBudgetButton = new Button("Новый бюджет", VaadinIcon.PLUS.create());
        createBudgetButton.addClassNames(
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Border.ALL,
                LumoUtility.BorderColor.PRIMARY_50
        );
        createBudgetButton.setWidthFull();
        createBudgetButton.setHeight(200, Unit.PIXELS);

        createBudgetButton.addClickListener(event -> eventPublisher.publishEvent(new OpenBudgetCreateDialogEvent(this)));
        content.add(
                new H2("Активные"),
                activeBudgetsCards,
                createBudgetButton
        );

        add(content);
    }
}
