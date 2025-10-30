package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.BudgetsDataProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.event.BudgetCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddToFavourite;
import hhz.ktoeto.moneymanager.ui.mixin.CanCreate;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@UIScope
@SpringComponent
public class ActiveBudgetsCardPresenter extends BudgetsCardsPresenter implements CanCreate, CanAddToFavourite<Budget> {

    private HasUpdatableData<List<BudgetCard>> hasUpdatableDataDelegate;

    public ActiveBudgetsCardPresenter(BudgetsDataProvider dataProvider, BudgetService budgetService,
                                      FormattingService formattingService, UserContextHolder userContextHolder,
                                      ApplicationEventPublisher eventPublisher) {
        super(dataProvider, budgetService, formattingService, userContextHolder, eventPublisher);
    }

    @Override
    public void initializeView() {
        ActiveBudgetsCardsView view = new ActiveBudgetsCardsView(this);
        this.view = view;
        this.hasUpdatableDataDelegate = view;

        this.dataProvider.addDataProviderListener(this);

        this.refresh();
    }

    @Override
    public void onDataChange(DataChangeEvent<Budget> dataChangeEvent) {
        this.refresh();
    }

    @Override
    public void onCreateRequested() {
        this.eventPublisher.publishEvent(new BudgetCreateRequested(this));
    }

    @Override
    public void onAddToFavourites(Budget budget) {
        budget.setFavourite(!budget.isFavourite());

        this.budgetService.update(budget, this.userContextHolder.getCurrentUserId());
    }

    private void refresh() {
        UI.getCurrent().access(() -> {
            List<BudgetCard> cards = dataProvider.fetch(BudgetFilter.activeBudgetsFilter())
                    .map(budget -> new BudgetCard(budget, formattingService))
                    .toList();

            this.hasUpdatableDataDelegate.update(cards);
        });
    }
}

