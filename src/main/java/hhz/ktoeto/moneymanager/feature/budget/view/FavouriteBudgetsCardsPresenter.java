package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.FavouriteBudgetsProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent

public class FavouriteBudgetsCardsPresenter extends BudgetsCardsPresenter {
    protected FavouriteBudgetsCardsPresenter(FavouriteBudgetsProvider dataProvider, BudgetService budgetService,
                                             FormattingService formattingService, UserContextHolder userContextHolder,
                                             ApplicationEventPublisher eventPublisher) {
        super(dataProvider, budgetService, formattingService, userContextHolder, eventPublisher);
    }

    @Override
    public void initialize() {
        this.view = new FavouriteBudgetsCardsView(this);

        this.dataProvider.addDataProviderListener(this);

        this.onDataChange(null);
    }
}
