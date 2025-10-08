package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.transaction.TransactionCreateDialog;
import hhz.ktoeto.moneymanager.ui.transaction.TransactionsSummary;
import hhz.ktoeto.moneymanager.ui.transaction.TransactionsGrid;
import hhz.ktoeto.moneymanager.utils.RouteName;
import jakarta.annotation.security.PermitAll;

@UIScope
@SpringComponent
@PermitAll
@Route(value = RouteName.MAIN, layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView(TransactionCreateDialog addTransactionModal,
                    TransactionsSummary summaryCards,
                    TransactionsGrid transactionsGrid) {
        Div container = new Div();
        container.setWidth("100%");
        container.setMaxWidth("900px");

        container.add(
                addTransactionModal,
                summaryCards,
                transactionsGrid
        );

        addClassNames(
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER
        );
        add(container);
    }
}
