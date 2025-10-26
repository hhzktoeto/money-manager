package hhz.ktoeto.moneymanager.feature;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridView;
import hhz.ktoeto.moneymanager.feature.transaction.view.TransactionsSummary;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.HOME, layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView(TransactionsGridView recentTransactionsGrid, TransactionsSummary transactionsSummary) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );

        FlexLayout content = new FlexLayout();
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE
        );

        BasicContainer transactionsGridContainer = new BasicContainer();
        transactionsGridContainer.setHeader("Недавние транзакции");
        transactionsGridContainer.setContent(recentTransactionsGrid.asComponent());
        transactionsGridContainer.getHeader().addClassName(LumoUtility.Margin.Bottom.MEDIUM);

        content.add(
                transactionsSummary,
                transactionsGridContainer
        );

        add(content);
    }
}
