package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.core.service.DateService;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.component.TransactionsSummaryCard;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreatedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.List;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionsSummary extends Composite<FlexLayout> {

    private final transient DateService dateService;
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

        Totals totals = calculateTotals();

        incomesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Доходы"))
                .mode(TransactionsSummaryCard.Mode.INCOME)
                .icon(VaadinIcon.TRENDING_UP)
                .initialAmount(totals.income())
                .build();
        expensesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Расходы"))
                .mode(TransactionsSummaryCard.Mode.EXPENSE)
                .icon(VaadinIcon.TRENDING_DOWN)
                .initialAmount(totals.expense())
                .build();
        balanceCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Баланс"))
                .mode(TransactionsSummaryCard.Mode.BALANCE)
                .icon(VaadinIcon.WALLET)
                .initialAmount(totals.balance())
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
        Totals totals = calculateTotals();

        incomesCard.setAmount(totals.income());
        expensesCard.setAmount(totals.expense());
        balanceCard.setAmount(totals.balance());
    }

    private Totals calculateTotals() {
        TransactionFilter currentMonthFilter = new TransactionFilter();
        currentMonthFilter.setFromDate(dateService.currentMonthStart());
        currentMonthFilter.setToDate(dateService.currentMonthEnd());
        List<Transaction> transactions = transactionService.getFiltered(userContextHolder.getCurrentUserId(), currentMonthFilter);

        BigDecimal income = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Totals(income, expense, income.subtract(expense));
    }

    private record Totals(BigDecimal income, BigDecimal expense, BigDecimal balance) {
    }
}
