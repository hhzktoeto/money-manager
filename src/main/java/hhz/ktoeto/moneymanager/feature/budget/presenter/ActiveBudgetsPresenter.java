package hhz.ktoeto.moneymanager.feature.budget.presenter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.budget.ActiveBudgetsView;
import hhz.ktoeto.moneymanager.feature.budget.ActiveBudgetsViewPresenter;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.budget.data.BudgetsDataProvider;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import lombok.Setter;

import java.util.List;

@UIScope
@SpringComponent
public class ActiveBudgetsPresenter implements ActiveBudgetsViewPresenter, DataProviderListener<Budget> {

    private final BudgetsDataProvider dataProvider;
    private final transient BudgetService budgetService;
    private final transient FormattingService formattingService;
    private final transient UserContextHolder userContextHolder;
    private final transient BudgetFormViewPresenter formPresenter;

    @Setter
    private transient ActiveBudgetsView view;

    public ActiveBudgetsPresenter(BudgetsDataProvider dataProvider,
                                  BudgetService budgetService,
                                  BudgetFormViewPresenter formPresenter,
                                  FormattingService formattingService,
                                  UserContextHolder userContextHolder) {
        this.dataProvider = dataProvider;
        this.budgetService = budgetService;
        this.formattingService = formattingService;
        this.userContextHolder = userContextHolder;
        this.formPresenter = formPresenter;

        this.dataProvider.addDataProviderListener(this);
    }

    @Override
    public void onDataChange(DataChangeEvent<Budget> dataChangeEvent) {
        this.refresh();
    }

    @Override
    public void onCreateRequested() {
        this.formPresenter.openCreateForm();
    }

    @Override
    public void onEditRequested(Budget budget) {
        this.formPresenter.openEditForm(budget);
    }

    @Override
    public void initialize() {
        this.refresh();
    }

    @Override
    public void onAddToFavourite(Budget budget) {
        budget.setFavourite(!budget.isFavourite());

        this.budgetService.update(budget, userContextHolder.getCurrentUserId());
    }

    private void refresh() {
        UI.getCurrent().access(() -> {
            Query<Budget, BudgetFilter> query = new Query<>(BudgetFilter.activeBudgetsFilter());
            List<BudgetCard> cards = dataProvider.fetch(query)
                    .map(budget -> new BudgetCard(budget, formattingService))
                    .toList();

            view.updateCards(cards);
        });
    }
}
