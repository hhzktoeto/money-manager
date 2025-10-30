package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.ExpiredBudgetsProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class ExpiredBudgetsCardsPresenter extends BudgetsCardsPresenter {

    public ExpiredBudgetsCardsPresenter(ExpiredBudgetsProvider dataProvider, BudgetService budgetService,
                                        FormattingService formattingService, UserContextHolder userContextHolder,
                                        ApplicationEventPublisher eventPublisher) {
        super(dataProvider, budgetService, formattingService, userContextHolder, eventPublisher);
    }

    @Override
    protected void setView() {
        this.view = new ExpiredBudgetsCardsView(this);
    }
}
