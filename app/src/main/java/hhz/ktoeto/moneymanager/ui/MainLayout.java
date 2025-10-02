package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UIScope
@SpringComponent
public class MainLayout extends Composite<VerticalLayout> implements RouterLayout {
    private final HorizontalLayout desktopNavigationContainer = new HorizontalLayout();
    private final HorizontalLayout mobileNavigationContainer = new HorizontalLayout();

    private final HorizontalLayout headerContainer = new HorizontalLayout();
    private final Div contentContainer = new Div();

    public MainLayout() {
        Image logo = new Image("logo.png", "Money Manager");
        logo.addClassName("app-logo");
        headerContainer.add(logo, desktopNavigationContainer);
        VerticalLayout root = this.getContent();
        root.addClassName("app-root");
        root.setPadding(false);
        root.setSpacing(false);

        List<RouterLink> desktopRouters = List.of(
                RouterUtils.createLink(DashboardView.class, "Главная", VaadinIcon.HOME.create()),
                RouterUtils.createLink(StatsView.class, "Статистика", VaadinIcon.PIE_BAR_CHART.create()),
                RouterUtils.createLink(PlanningView.class, "Планирование", VaadinIcon.CALC_BOOK.create())
        );
        desktopRouters.forEach(router -> {
            router.addClassName("app-desktop-nav-buttons");
            desktopNavigationContainer.add(router);
        });

        List<RouterLink> mobileRouters = List.of(
                RouterUtils.createLink(DashboardView.class, VaadinIcon.HOME.create()),
                RouterUtils.createLink(StatsView.class, VaadinIcon.PIE_BAR_CHART.create()),
                RouterUtils.createLink(PlanningView.class, VaadinIcon.CALC_BOOK.create())
        );
        mobileRouters.forEach(router -> {
            router.addClassName("app-mobile-nav-buttons");
            mobileNavigationContainer.add(router);
        });

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
