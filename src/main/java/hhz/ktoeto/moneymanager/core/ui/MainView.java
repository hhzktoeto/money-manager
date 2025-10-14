package hhz.ktoeto.moneymanager.core.ui;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.core.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.feature.transaction.ui.RecentTransactionsGrid;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.MAIN, layout = MainLayout.class)
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
