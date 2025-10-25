package hhz.ktoeto.moneymanager.ui.feature.budget.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.ui.feature.budget.view.data.BudgetsDataProvider;
import hhz.ktoeto.moneymanager.ui.feature.budget.view.form.BudgetFormViewPresenter;
import lombok.Setter;

import java.util.List;

@UIScope
@SpringComponent
public class ActiveBudgetsViewPresenter implements DataProviderListener<Budget> {

    private final BudgetsDataProvider dataProvider;
    private final transient BudgetFormViewPresenter formPresenter;
    private final transient FormattingService formattingService;

    @Setter
    private ActiveBudgetsView view;

    public ActiveBudgetsViewPresenter(BudgetsDataProvider dataProvider,
                                      BudgetFormViewPresenter formPresenter,
                                      FormattingService formattingService) {
        this.dataProvider = dataProvider;
        this.formPresenter = formPresenter;
        this.formattingService = formattingService;

        this.dataProvider.addDataProviderListener(this);
    }

    @Override
    public void onDataChange(DataChangeEvent<Budget> dataChangeEvent) {
        this.refresh();
    }

    void onCreateRequested() {
        formPresenter.openCreateForm();
    }

    void onEditRequested(Budget budget) {
        formPresenter.openEditForm(budget);
    }

    void init() {
        this.refresh();
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
