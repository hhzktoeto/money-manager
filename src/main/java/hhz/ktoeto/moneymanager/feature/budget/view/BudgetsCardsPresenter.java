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
import hhz.ktoeto.moneymanager.ui.event.BudgetCreateRequested;
import hhz.ktoeto.moneymanager.ui.event.BudgetEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public abstract class BudgetsCardsPresenter implements ViewPresenter, DataProviderListener<Budget>, CanEdit<Budget>, CanCreate,
        CanFormatAmount, CanFormatDate, CanAddToFavourite<Budget> {

    private final BudgetsDataProvider dataProvider;
    private final transient BudgetService budgetService;
    private final transient FormattingService formattingService;
    private final transient UserContextHolder userContextHolder;
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
            List<Budget> budgets = this.dataProvider.fetch().toList();
            this.view.update(budgets);
        });
    }

    @Override
    public void onEditRequested(Budget budget) {
        this.eventPublisher.publishEvent(new BudgetEditRequested(this, budget));
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

    @Override
    public String formatAmount(BigDecimal amount) {
        return this.formattingService.formatAmount(amount);
    }

    @Override
    public String formatDate(LocalDate date) {
        return this.formattingService.formatDate(date);
    }
}
