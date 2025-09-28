package hhz.ktoeto.moneymanager.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.constant.RouteName;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@UIScope
@Component
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final Tabs menu;
    private final Map<String, Tab> tabsByRoute = new HashMap<>();

    public MainLayout() {
        H1 title = new H1("Money Manager");

        DrawerToggle toggle = new DrawerToggle();

        Button logOut = new Button(new Icon(VaadinIcon.SIGN_OUT));
        logOut.getElement().setAttribute("aria-label", "Выйти");
        logOut.addClickListener(ignored -> log.info("Чел нажал на выйти!"));

        HorizontalLayout header = new HorizontalLayout(toggle, title, logOut);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
        Tabs tabs = createMenuTabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);

        this.menu = tabs;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        String path = afterNavigationEvent.getLocation().getPath();
        Tab tab = tabsByRoute.getOrDefault(path, tabsByRoute.get(""));
        menu.setSelectedTab(tab);
    }

    private Tabs createMenuTabs() {
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.add(createTab("Главная", DashboardView.class, RouteName.MAIN));
        tabs.add(createTab("Статистика", StatsView.class, RouteName.STATS));
        tabs.add(createTab("Планирование", PlanningView.class, RouteName.PLANNING));

        return tabs;
    }

    private Tab createTab(String title, Class<? extends com.vaadin.flow.component.Component> viewClass, String routePath) {
        RouterLink link = new RouterLink(title, viewClass);
        link.setHighlightCondition(HighlightConditions.sameLocation());
        Tab tab = new Tab(link);
        tabsByRoute.put(routePath, tab);
        return tab;
    }
}
