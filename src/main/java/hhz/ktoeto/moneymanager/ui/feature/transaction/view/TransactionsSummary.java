package hhz.ktoeto.moneymanager.ui.feature.transaction.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.ui.component.TransactionsSummaryCard;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionsSummaries;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.ui.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.ui.event.TransactionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionsSummary extends Composite<FlexLayout> {

    private final transient UserContextHolder userContextHolder;
    private final transient FormattingService formattingService;
    private final transient TransactionService transactionService;

    private TransactionsSummaryCard incomesCard;
    private TransactionsSummaryCard expensesCard;
    private TransactionsSummaryCard balanceCard;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.XLARGE,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );

        TransactionsSummaries summaries = transactionService.getSummaries(
                userContextHolder.getCurrentUserId(),
                TransactionFilter.currentMonthFilter()
        );

        incomesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Доходы"))
                .mode(TransactionsSummaryCard.Mode.INCOME)
                .icon(VaadinIcon.TRENDING_UP)
                .amount(summaries.incomes())
                .build();
        expensesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Расходы"))
                .mode(TransactionsSummaryCard.Mode.EXPENSE)
                .icon(VaadinIcon.TRENDING_DOWN)
                .amount(summaries.expenses())
                .build();
        balanceCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Баланс"))
                .mode(TransactionsSummaryCard.Mode.BALANCE)
                .icon(VaadinIcon.WALLET)
                .amount(summaries.total())
                .build();

        root.add(incomesCard, expensesCard, balanceCard);

        return root;
    }

    @EventListener({
            TransactionCreatedEvent.class,
            TransactionUpdatedEvent.class,
            TransactionDeletedEvent.class
    })
    private void updateSummary() {
        TransactionsSummaries summaries = transactionService.getSummaries(
                userContextHolder.getCurrentUserId(),
                TransactionFilter.currentMonthFilter()
        );

        incomesCard.setAmount(summaries.incomes());
        expensesCard.setAmount(summaries.expenses());
        balanceCard.setAmount(summaries.total());
    }
}
