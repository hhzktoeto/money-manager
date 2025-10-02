package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class MainLayout extends Composite<VerticalLayout> implements RouterLayout {
    private final HorizontalLayout desktopNavigationContainer = new HorizontalLayout(
            RouterUtils.createLink(DashboardView.class, "Главная", VaadinIcon.HOME),
            RouterUtils.createLink(StatsView.class, "Статистика", VaadinIcon.PIE_BAR_CHART),
            RouterUtils.createLink(PlanningView.class, "Планирование", VaadinIcon.CALC_BOOK)
    );
    private final HorizontalLayout mobileNavigationContainer = new HorizontalLayout(
            RouterUtils.createLink(DashboardView.class, VaadinIcon.HOME),
            RouterUtils.createLink(StatsView.class, VaadinIcon.PIE_BAR_CHART),
            RouterUtils.createLink(PlanningView.class, VaadinIcon.CALC_BOOK)
    );

    private final HorizontalLayout headerContainer = new HorizontalLayout(new H1("Money Manager"), desktopNavigationContainer);
    private final Div contentContainer = new Div();

    public MainLayout() {
        VerticalLayout root = this.getContent();
        root.addClassName("app-root");
        root.setPadding(false);
        root.setSpacing(false);

        headerContainer.addClassName("app-header");
        desktopNavigationContainer.addClassName("app-desktop-nav");
        contentContainer.addClassName("app-content");
        root.setFlexGrow(1, contentContainer);
        mobileNavigationContainer.addClassName("app-mobile-nav");

        root.add(headerContainer, contentContainer, mobileNavigationContainer);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            contentContainer.getElement().removeAllChildren();
            contentContainer.getElement().appendChild(content.getElement());
        }
    }
}
