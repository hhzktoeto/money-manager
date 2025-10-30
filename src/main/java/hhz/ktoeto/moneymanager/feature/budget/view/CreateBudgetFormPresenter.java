package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormView;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.event.BudgetCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateBudgetFormPresenter extends AbstractBudgetFormViewPresenter {

    public CreateBudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                        ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
        super(budgetService, userContextHolder, eventPublisher, categoryDataProvider);
    }

    @Override
    protected String getDialogTitle() {
        return "Новый бюджет";
    }

    @Override
    protected BudgetFormView getForm() {
        return new CreateBudgetForm(this, categoryDataProvider);
    }

    @Override
    public void onSubmit() {
        long userId = this.userContextHolder.getCurrentUserId();

        Budget budget = new Budget();
        budget.setUserId(userId);

        boolean valid = view.writeToIfValid(budget);
        if (!valid) {
            return;
        }

        this.budgetService.create(budget);
        this.dialog.close();
    }

    @EventListener(BudgetCreateRequested.class)
    private void onBudgetCreateRequested() {
        this.openForm(new Budget());
    }
}
