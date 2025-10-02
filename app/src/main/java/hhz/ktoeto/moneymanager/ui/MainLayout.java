package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@UIScope
@SpringComponent
public class MainLayout extends Composite<Div>
        implements RouterLayout, AfterNavigationObserver {

    private final Div outlet = new Div();                        // сюда рендерятся вьюхи
    private final Tabs tabs = new Tabs();                        // вкладки навигации
    private final Map<Tab, Class<? extends Component>> routeByTab = new LinkedHashMap<>();

    // наш выезжающий блок и фон
    private final Div menuPanel = new Div();
    private final Div backdrop = new Div();
    private boolean menuOpen = false;

    public MainLayout() {
        // ---------- ШАПКА ----------
        Button toggle = new Button(VaadinIcon.MENU.create(), e -> toggleMenu());
        toggle.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);

        H1 title = new H1("Money Manager");

        Div navbar = new Div(toggle, title);
        navbar.addClassNames("container", "app-navbar");
        navbar.getStyle().set("height", "var(--app-navbar-height)");

        // ---------- КОНТЕНТ ----------
        outlet.setWidthFull();
        Div container = new Div(outlet);
        container.addClassName("container");

        Div root = getContent();
        root.setSizeFull();
        root.add(navbar, container);

        // ---------- МЕНЮ (собственный «sheet» без Dialog) ----------
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addClassName("top-menu-tabs");
        tabs.setWidthFull();

        addTab("Главная", VaadinIcon.HOME,       DashboardView.class);
        addTab("Статистика", VaadinIcon.PIE_BAR_CHART, StatsView.class);
        addTab("Планирование", VaadinIcon.CALC_BOOK,   PlanningView.class);

        tabs.addSelectedChangeListener(e -> {
            Tab sel = e.getSelectedTab();
            if (sel != null) {
                Class<? extends Component> target = routeByTab.get(sel);
                if (target != null) {
                    UI.getCurrent().navigate(target);
                    closeMenu();
                }
            }
        });

        // панель
        menuPanel.addClassName("top-menu");
        menuPanel.add(new Scroller(tabs));

        // фон
        backdrop.addClassName("top-menu-backdrop");
        backdrop.addClickListener(e -> closeMenu()); // клик по фону закрывает меню

        // кладём поверх всего (z-index берём из css)
        root.add(menuPanel, backdrop);
    }

    private void addTab(String text, VaadinIcon icon, Class<? extends Component> view) {
        Tab t = new Tab(icon.create(), new Span(text));
        routeByTab.put(t, view);
        tabs.add(t);
    }

    private void openMenu() {
        menuOpen = true;
        menuPanel.getClassNames().add("open");
        backdrop.getClassNames().add("open");
    }

    private void closeMenu() {
        menuOpen = false;
        menuPanel.getClassNames().remove("open");
        backdrop.getClassNames().remove("open");
    }

    private void toggleMenu() {
        if (menuOpen) closeMenu(); else openMenu();
    }

    // Куда класть дочерние вьюхи
    @Override
    public void showRouterLayoutContent(HasElement content) {
        outlet.getElement().removeAllChildren();
        if (content != null) outlet.getElement().appendChild(content.getElement());
    }

    // Подсветка активной вкладки и автозакрытие меню
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Class<?> target = event.getLocation().getClass();
        routeByTab.entrySet().stream()
                .filter(en -> en.getValue().equals(target))
                .findFirst()
                .ifPresent(en -> tabs.setSelectedTab(en.getKey()));
        if (menuOpen) closeMenu();
    }
}
