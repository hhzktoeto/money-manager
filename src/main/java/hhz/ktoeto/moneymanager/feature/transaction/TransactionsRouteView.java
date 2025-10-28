package hhz.ktoeto.moneymanager.feature.transaction;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.TRANSACTIONS, layout = MainLayout.class)
public class TransactionsRouteView extends VerticalLayout {

    public TransactionsRouteView(TransactionsGridSettingsView gridSettings, TransactionsGridView allTransactionsGrid) {
        this.setPadding(false);
        this.setSpacing(false);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.START,
                LumoUtility.JustifyContent.START
        );
        this.add(
                gridSettings.asComponent(),
                allTransactionsGrid.asComponent()
        );
    }
}
