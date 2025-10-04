package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.component.AddTransactionModal;
import hhz.ktoeto.moneymanager.ui.view.MainView;
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

    private final Button desktopAddTransactionButton = new Button("Добавить транзакцию");
    private final Button mobileAddTransactionButton = new Button(VaadinIcon.PLUS.create());
    private final Image appLogo = new Image("logo.png", "Money Manager");

    public MainLayout(AddTransactionModal addTransactionModal) {
        VerticalLayout root = this.getContent();
        root.addClassName("root");
        root.setPadding(false);
        root.setSpacing(false);

        appLogo.addClickListener(event -> UI.getCurrent().navigate(MainView.class));

        ComponentEventListener<ClickEvent<Button>> openAddTransaction = e -> addTransactionModal.open();
        desktopAddTransactionButton.addClickListener(openAddTransaction);
        mobileAddTransactionButton.addClickListener(openAddTransaction);

        List<RouterLink> desktopRouters = List.of(
                RouterUtils.createLink(MainView.class, "Главная"),
                RouterUtils.createLink(StatsView.class, "Статистика"),
                RouterUtils.createLink(PlanningView.class, "Планирование")
        );
        desktopRouters.forEach(router -> {
            router.addClassName("desktop-nav-buttons");
            router.setHighlightCondition(HighlightConditions.sameLocation());
            desktopNavigationContainer.add(router);
        });

        List<RouterLink> mobileRouters = List.of(
                RouterUtils.createLink(MainView.class, VaadinIcon.HOME.create()),
                RouterUtils.createLink(StatsView.class, VaadinIcon.PIE_BAR_CHART.create()),
                RouterUtils.createLink(PlanningView.class, VaadinIcon.CALC_BOOK.create())
        );
        mobileRouters.forEach(router -> {
            router.addClassName("mobile-nav-buttons");
            router.setHighlightCondition(HighlightConditions.sameLocation());
            mobileNavigationContainer.add(router);
        });

        appLogo.addClassName("logo");
        headerContainer.addClassName("header");
        desktopNavigationContainer.addClassName("desktop-nav");
        mobileNavigationContainer.addClassName("mobile-nav");
        desktopAddTransactionButton.addClassName("desktop-add-transaction-button");
        mobileAddTransactionButton.addClassName("mobile-add-transaction-button");
        contentContainer.addClassName("content");

        headerContainer.add(appLogo, desktopNavigationContainer, desktopAddTransactionButton);
        root.setFlexGrow(1, contentContainer);
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
