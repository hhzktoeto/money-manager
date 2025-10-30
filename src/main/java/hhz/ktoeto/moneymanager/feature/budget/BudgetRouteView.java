package hhz.ktoeto.moneymanager.feature.budget;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.view.ActiveBudgetsCardPresenter;
import hhz.ktoeto.moneymanager.feature.budget.view.ExpiredBudgetsCardsPresenter;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.BUDGET, layout = MainLayout.class)
public class BudgetRouteView extends VerticalLayout {

    public BudgetRouteView(ActiveBudgetsCardPresenter activeBudgetsPresenter,
                           ExpiredBudgetsCardsPresenter expiredBudgetsPresenter) {
        this.setPadding(false);
        this.setSpacing(false);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Gap.MEDIUM
        );
        this.add(activeBudgetsPresenter.getView().asComponent());

        Details archiveBudgetsDetails = new Details("Архив");
        archiveBudgetsDetails.setSizeFull();
        archiveBudgetsDetails.add(expiredBudgetsPresenter.getView().asComponent());
        archiveBudgetsDetails.getSummary().addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.TextColor.BODY
        );

        this.add(archiveBudgetsDetails);
    }
}
