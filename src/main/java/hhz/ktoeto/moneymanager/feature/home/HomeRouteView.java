package hhz.ktoeto.moneymanager.feature.home;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.home.view.TransactionsSummary;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.HOME, layout = MainLayout.class)
public class HomeRouteView extends VerticalLayout {

    public HomeRouteView(TransactionsGridView recentTransactionsGrid, TransactionsSummary transactionsSummary) {
        BasicContainer transactionsGridContainer = new BasicContainer();
        Details transactionsGridDetails = new Details("Недавние транзакции");
        transactionsGridDetails.add(recentTransactionsGrid.asComponent());
        transactionsGridDetails.setOpened(true);
        transactionsGridDetails.setSizeFull();
        transactionsGridDetails.getSummary().addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.TextColor.BODY
        );
        transactionsGridContainer.setHeader(transactionsGridDetails);
        transactionsGridContainer.getHeader().addClassNames(
                LumoUtility.Padding.Top.SMALL,
                LumoUtility.Padding.Left.MEDIUM
        );

        this.setPadding(false);
        this.setSpacing(false);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Gap.MEDIUM
        );
        this.add(
                transactionsSummary,
                transactionsGridContainer
        );
    }
}
