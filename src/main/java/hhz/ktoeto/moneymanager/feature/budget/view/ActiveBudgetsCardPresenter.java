package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.ActiveBudgetsProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.event.BudgetCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddToFavourite;
import hhz.ktoeto.moneymanager.ui.mixin.CanCreate;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class ActiveBudgetsCardPresenter extends BudgetsCardsPresenter implements CanCreate, CanAddToFavourite<Budget> {

    public ActiveBudgetsCardPresenter(ActiveBudgetsProvider dataProvider, BudgetService budgetService,
                                      FormattingService formattingService, UserContextHolder userContextHolder,
                                      ApplicationEventPublisher eventPublisher) {
        super(dataProvider, budgetService, formattingService, userContextHolder, eventPublisher);
    }

    @Override
    protected void preInitialize() {
        this.setView(new ActiveBudgetsCardsView(this));
    }

    @Override
    public void onCreateRequested() {
        this.getEventPublisher().publishEvent(new BudgetCreateRequested(this));
    }

    @Override
    public void onAddToFavourites(Budget budget) {
        budget.setFavourite(!budget.isFavourite());

        this.getBudgetService().update(budget, this.getUserContextHolder().getCurrentUserId());
    }
}

