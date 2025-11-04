package hhz.ktoeto.moneymanager.feature.budget.formview;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.event.BudgetCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class CreateBudgetFormPresenter extends BudgetFormPresenter {

    public CreateBudgetFormPresenter(BudgetService budgetService, UserContextHolder userContextHolder,
                                        ApplicationEventPublisher eventPublisher, SimpleCategoriesProvider categoryDataProvider) {
        super(budgetService, userContextHolder, eventPublisher, categoryDataProvider);
    }

    @Override
    public void initialize() {
        this.setView(new CreateBudgetFormView(this, this.getCategoryDataProvider()));
    }

    @Override
    protected String getDialogTitle() {
        return "Новый бюджет";
    }

    @Override
    public void onSubmit() {
        long userId = this.getUserContextHolder().getCurrentUserId();

        Budget budget = new Budget();
        budget.setUserId(userId);

        boolean valid = this.getView().writeToIfValid(budget);
        if (!valid) {
            return;
        }

        this.getBudgetService().create(budget);
        this.getRootDialog().close();
    }

    @EventListener(BudgetCreateRequested.class)
    private void onBudgetCreateRequested() {
        this.openForm(new Budget());
    }
}
