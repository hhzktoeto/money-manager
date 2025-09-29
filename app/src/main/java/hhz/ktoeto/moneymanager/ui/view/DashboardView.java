package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.constant.RouteName;
import hhz.ktoeto.moneymanager.ui.component.container.AddTransactionContainer;
import hhz.ktoeto.moneymanager.ui.component.SummaryCards;
import hhz.ktoeto.moneymanager.ui.component.TransactionsGrid;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@SpringComponent
@PermitAll
@Route(value = RouteName.MAIN, layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    public DashboardView(AddTransactionContainer addTransactionContainer,
                         SummaryCards summaryCards,
                         TransactionsGrid transactionsGrid) {
        Div container = new Div();
        container.setWidth("100%");
        container.setMaxWidth("900px");
        container.getStyle().set("spacing", "10px");

        Card transactionsGridCard = new Card();
        transactionsGridCard.add(transactionsGrid);
        transactionsGridCard.setSizeFull();

        setSpacing(true);
        setPadding(true);
        setHeightFull();
        setAlignItems(Alignment.CENTER);

        container.add(
                addTransactionContainer,
                summaryCards,
                transactionsGridCard
        );

        add(container);
    }
}
