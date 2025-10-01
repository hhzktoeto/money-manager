package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 title = new H1("Money Manager");
        HorizontalLayout navigation = new HorizontalLayout(
                RouterUtils.createLink(null,  DashboardView.class, VaadinIcon.HOME),
                RouterUtils.createLink(null, StatsView.class, VaadinIcon.PIE_BAR_CHART),
                RouterUtils.createLink(null, PlanningView.class, VaadinIcon.CALC_BOOK)
        );

        navigation.addClassNames(
                LumoUtility.Position.ABSOLUTE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Height.MEDIUM,
                LumoUtility.Gap.LARGE
        );

        addToNavbar(title);
        addToNavbar(true, navigation);
    }
}
