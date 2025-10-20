package hhz.ktoeto.moneymanager.ui.feature.budget.ui.data;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.BudgetCreatedEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@VaadinSessionScope
public class BudgetsDataProvider extends ListDataProvider<Budget> {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;

    public BudgetsDataProvider(BudgetService budgetService, UserContextHolder userContextHolder) {
        super(new ArrayList<>());
        this.budgetService = budgetService;
        this.userContextHolder = userContextHolder;
    }

    @PostConstruct
    private void loadData() {
        VaadinSession.getCurrent().getUIs().forEach(ui -> ui.access(() -> {
            this.getItems().clear();
            this.getItems().addAll(
                    budgetService.getActiveBudgets(userContextHolder.getCurrentUserId())
            );
            this.refreshAll();
        }));
    }

    @EventListener(BudgetCreatedEvent.class)
    private void onBudgetCreated(BudgetCreatedEvent event) {
        List<Budget> items = (List<Budget>) this.getItems();
        items.add(event.getBudget());
        this.refreshAll();
    }
}
