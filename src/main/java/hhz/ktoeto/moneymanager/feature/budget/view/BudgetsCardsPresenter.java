package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.data.provider.DataProviderListener;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.BudgetsDataProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.event.BudgetEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BudgetsCardsPresenter implements ViewPresenter, DataProviderListener<Budget>, CanEdit<Budget> {

    protected final BudgetsDataProvider dataProvider;
    protected final transient BudgetService budgetService;
    protected final transient FormattingService formattingService;
    protected final transient UserContextHolder userContextHolder;
    protected final transient ApplicationEventPublisher eventPublisher;

    @Getter
    protected transient BudgetsCardsView view;

    public BudgetsCardsPresenter(BudgetsDataProvider dataProvider, BudgetService budgetService,
                                 FormattingService formattingService, UserContextHolder userContextHolder,
                                 ApplicationEventPublisher eventPublisher) {
        this.dataProvider = dataProvider;
        this.budgetService = budgetService;
        this.formattingService = formattingService;
        this.userContextHolder = userContextHolder;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @PostConstruct
    public abstract void initialize();

    @Override
    public void onEditRequested(Budget entity) {
        this.eventPublisher.publishEvent(new BudgetEditRequested(this, entity));
    }
}
