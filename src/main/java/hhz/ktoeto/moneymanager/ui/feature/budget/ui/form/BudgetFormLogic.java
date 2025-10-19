package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetService;
import hhz.ktoeto.moneymanager.ui.feature.category.event.OpenCategoryCreateDialogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@SpringComponent
@RequiredArgsConstructor
public class BudgetFormLogic {

    private final BudgetService budgetService;
    private final UserContextHolder userContextHolder;
    private final ApplicationEventPublisher eventPublisher;

    void submitCreate(BudgetForm form) {

    }

    void cancelCreate() {

    }

    void addCategory() {
        eventPublisher.publishEvent(new OpenCategoryCreateDialogEvent(this));
    }
}
