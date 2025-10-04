package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.utils.RouteName;
import hhz.ktoeto.moneymanager.ui.component.container.AddTransactionContainer;
import hhz.ktoeto.moneymanager.ui.component.SummaryCards;
import hhz.ktoeto.moneymanager.ui.component.TransactionsGrid;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@SpringComponent
@PermitAll
@Route(value = RouteName.MAIN, layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView(AddTransactionContainer addTransactionContainer,
                    SummaryCards summaryCards,
                    TransactionsGrid transactionsGrid) {
        Div container = new Div();
        container.setWidth("100%");
        container.setMaxWidth("900px");

        Card transactionsGridCard = new Card();
        transactionsGridCard.add(transactionsGrid);
        transactionsGridCard.setSizeFull();

        container.add(
                addTransactionContainer,
                summaryCards,
                transactionsGridCard
        );

        addClassNames(
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER
        );
        add(container);
    }
}
