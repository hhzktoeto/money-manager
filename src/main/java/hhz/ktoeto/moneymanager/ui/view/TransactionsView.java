package hhz.ktoeto.moneymanager.ui.view;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.ui.transaction.AllTransactionsGrid;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import jakarta.annotation.security.PermitAll;

@UIScope
@SpringComponent
@PermitAll
@Route(value = RouterUtils.RouteName.TRANSACTIONS, layout = MainLayout.class)
public class TransactionsView extends VerticalLayout {

    public TransactionsView(AllTransactionsGrid transactionsGrid) {
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

        HorizontalLayout header = new HorizontalLayout();


        BasicContainer transactionsGridContainer = new BasicContainer();
        transactionsGridContainer.setContent(transactionsGrid);
        content.add(transactionsGridContainer);

        add(content);
    }
}
