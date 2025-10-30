package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.event.BudgetEditRequested;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class EditBudgetFormPresenter extends BudgetFormPresenter {

    protected EditBudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                      ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        super(budgetService, userContextHolder, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initializeView() {
        this.view = new EditBudgetFormView(this, this.categoryDataProvider);
    }

    @Override
    protected String getDialogTitle() {
        return "Редактировать бюджет";
    }

    @Override
    public void onSubmit() {
        Budget budget = view.getEntity();

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        budgetService.update(budget, userContextHolder.getCurrentUserId());
        this.dialog.close();
    }

    @EventListener(BudgetEditRequested.class)
    private void onBudgetEditRequested(BudgetEditRequested event) {
        this.openForm(event.getBudget());
    }
}
