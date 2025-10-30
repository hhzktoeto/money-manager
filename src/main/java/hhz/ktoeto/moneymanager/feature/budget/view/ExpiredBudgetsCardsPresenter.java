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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

import java.util.List;

@UIScope
@SpringComponent
public class ExpiredBudgetsCardsPresenter extends BudgetsCardsPresenter {

    public ExpiredBudgetsCardsPresenter(BudgetsDataProvider dataProvider, BudgetService budgetService,
                                        FormattingService formattingService, UserContextHolder userContextHolder,
                                        ApplicationEventPublisher eventPublisher) {
        super(dataProvider, budgetService, formattingService, userContextHolder, eventPublisher);
    }

    @Override
    public void initialize() {
        this.view = new ExpiredBudgetsCardsView(this);

        this.dataProvider.addDataProviderListener(this);

        this.refresh();
    }

    @Override
    public void onDataChange(DataChangeEvent<Budget> dataChangeEvent) {
        this.refresh();
    }

    private void refresh() {
        UI.getCurrent().access(() -> {
            List<BudgetCard> cards = this.dataProvider.fetchWithSort(
                            BudgetFilter.expiredBudgetsFilter(),
                            Sort.by(Sort.Direction.ASC, "endDate")
                    )
                    .map(budget -> new BudgetCard(budget, this.formattingService))
                    .toList();

            this.view.update(cards);
        });
    }
}
