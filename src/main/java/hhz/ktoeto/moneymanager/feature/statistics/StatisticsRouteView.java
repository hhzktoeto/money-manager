package hhz.ktoeto.moneymanager.feature.statistics;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.view.ExpensesPiePresenter;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.ui.core.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.STATISTICS, layout = MainLayout.class)
public class StatisticsRouteView extends VerticalLayout {

    public StatisticsRouteView(ExpensesPiePresenter expensesPiePresenter) {
        this.setPadding(false);
        this.setSpacing(false);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.START,
                LumoUtility.JustifyContent.START
        );

        this.add(expensesPiePresenter.getView().asComponent(), expensesPiePresenter.getSoCharts().asComponent());
    }
}
