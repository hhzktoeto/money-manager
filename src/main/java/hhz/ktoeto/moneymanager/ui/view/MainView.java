package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.transaction.RecentTransactionsGrid;
import hhz.ktoeto.moneymanager.ui.transaction.TransactionsSummary;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import jakarta.annotation.security.PermitAll;

@UIScope
@SpringComponent
@PermitAll
@Route(value = RouterUtils.RouteName.MAIN, layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView(TransactionsSummary summaryCards,
                    RecentTransactionsGrid transactionsGrid) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );

        Div container = new Div();
        container.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE
        );

        container.add(
                summaryCards,
                transactionsGrid
        );

        add(container);
    }
}
