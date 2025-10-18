package hhz.ktoeto.moneymanager.ui.feature.budget.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.OpenBudgetCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.BudgetForm;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.BudgetFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class BudgetDialog extends Composite<CustomDialog> {

    private final transient BudgetFormFactory formFactory;

    @EventListener(OpenBudgetCreateDialogEvent.class)
    private void openBudgetCreation() {
        this.getContent().open();

        this.getContent().setTitle("Создать бюджет");
        BudgetForm form = formFactory.budgetCreateForm();
        this.getContent().addBody(form);
    }
}
