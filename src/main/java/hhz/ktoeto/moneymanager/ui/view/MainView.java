package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.transaction.RecentTransactionsGrid;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = RouterUtils.RouteName.MAIN, layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView(RecentTransactionsGrid transactionsGrid) {
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
        transactionsGridContainer.setContent(transactionsGrid);
        transactionsGridContainer.getHeader().addClassName(LumoUtility.Margin.Bottom.MEDIUM);

        content.add(
                transactionsGridContainer
        );

        add(content);
    }
}
