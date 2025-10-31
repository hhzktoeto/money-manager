package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.data.BudgetsDataProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.event.BudgetEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;

import java.util.List;

public abstract class BudgetsCardsPresenter implements ViewPresenter, DataProviderListener<Budget>, CanEdit<Budget> {

    private final BudgetsDataProvider dataProvider;
    @Getter(AccessLevel.PROTECTED)
    private final transient BudgetService budgetService;
    private final transient FormattingService formattingService;
    @Getter(AccessLevel.PROTECTED)
    private final transient UserContextHolder userContextHolder;
    @Getter(AccessLevel.PROTECTED)
    private final transient ApplicationEventPublisher eventPublisher;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private transient BudgetsCardsView view;

    protected BudgetsCardsPresenter(BudgetsDataProvider dataProvider, BudgetService budgetService,
                                 FormattingService formattingService, UserContextHolder userContextHolder,
                                 ApplicationEventPublisher eventPublisher) {
        this.dataProvider = dataProvider;
        this.budgetService = budgetService;
        this.formattingService = formattingService;
        this.userContextHolder = userContextHolder;
        this.eventPublisher = eventPublisher;
    }

    protected abstract void preInitialize();

    @Override
    @PostConstruct
    public void initialize() {
        this.preInitialize();
        this.dataProvider.addDataProviderListener(this);
        // Call on init to make cards visible without updating
        this.onDataChange(null);
    }

    @Override
    public void onDataChange(@Nullable DataChangeEvent<Budget> ignored) {
        UI.getCurrent().access(() -> {
            List<BudgetCard> cards = this.dataProvider.fetch()
                    .map(budget -> new BudgetCard(budget, this.formattingService))
                    .toList();

            this.view.update(cards);
        });
    }

    @Override
    public void onEditRequested(Budget entity) {
        this.eventPublisher.publishEvent(new BudgetEditRequested(this, entity));
    }
}
