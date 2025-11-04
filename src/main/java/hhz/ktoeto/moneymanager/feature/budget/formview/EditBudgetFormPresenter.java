package hhz.ktoeto.moneymanager.feature.budget.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.event.BudgetEditRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class EditBudgetFormPresenter extends BudgetFormPresenter {

    protected EditBudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                      ApplicationEventPublisher eventPublisher, SimpleAllCategoriesProvider categoryDataProvider) {
        super(budgetService, userContextHolder, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initialize() {
        this.setView(new EditBudgetFormView(this, this.getCategoryDataProvider()));
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать бюджет";
    }

    @Override
    public void onSubmit() {
        Budget budget = this.getView().getEntity();

        boolean valid = this.getView().writeToIfValid(budget);
        if (!valid) {
            return;
        }

        this.getBudgetService().update(budget, this.getUserContextHolder().getCurrentUserId());
        this.getRootDialog().close();
    }

    @EventListener(BudgetEditRequested.class)
    private void onBudgetEditRequested(BudgetEditRequested event) {
        this.openForm(event.getBudget());
    }
}
