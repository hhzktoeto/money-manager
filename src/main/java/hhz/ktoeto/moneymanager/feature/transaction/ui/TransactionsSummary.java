package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.component.TransactionsSummaryCard;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionsSummary extends Composite<FlexLayout> {

    private final transient FormattingService formattingService;
    private final transient TransactionDataProvider allTransactionsProvider;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.XLARGE,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );

        List<Transaction> transactions = allTransactionsProvider.getTransactions();

        BigDecimal income = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balance = income.subtract(expense);


        TransactionsSummaryCard incomesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Доходы"))
                .mode(TransactionsSummaryCard.Mode.INCOME)
                .icon(VaadinIcon.TRENDING_UP)
                .amount(income)
                .build();
        TransactionsSummaryCard expensesCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Расходы"))
                .mode(TransactionsSummaryCard.Mode.EXPENSE)
                .icon(VaadinIcon.TRENDING_DOWN)
                .amount(expense)
                .build();
        TransactionsSummaryCard balanceCard = TransactionsSummaryCard.builder()
                .formattingService(formattingService)
                .title(new H3("Баланс"))
                .mode(TransactionsSummaryCard.Mode.BALANCE)
                .icon(VaadinIcon.WALLET)
                .amount(balance)
                .build();

        root.add(incomesCard, expensesCard, balanceCard);

        return root;
    }
}
