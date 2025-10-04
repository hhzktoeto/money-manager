package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@UIScope
@SpringComponent
public class SummaryCards extends HorizontalLayout {

    private final transient TransactionService transactionService;

    private static final class SummaryCard extends ComponentContainer {
    }

    private final SummaryCard incomesCard = new SummaryCard();
    private final SummaryCard expensesCard = new SummaryCard();
    private final SummaryCard totalCard = new SummaryCard();

    public SummaryCards(TransactionService transactionService) {
        this.transactionService = transactionService;

        addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XLARGE
        );

        refresh();
        add(incomesCard, expensesCard, totalCard);
    }

    @EventListener({
            TransactionAddedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class}
    )
    private void onTransactionUpdate(TransactionAddedEvent ignored) {
        UI.getCurrent().access(this::refresh);
    }

    private void refresh() {
        List<Transaction> transactions = transactionService.getAll(SecurityUtils.getCurrentUser().getId());

        BigDecimal incomes = transactions.stream()
                .filter(t -> Objects.equals(Transaction.Type.INCOME, t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal expenses = transactions.stream()
                .filter(t -> Objects.equals(Transaction.Type.EXPENSE, t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal total = incomes.subtract(expenses);

        incomesCard.removeAll();
        expensesCard.removeAll();
        totalCard.removeAll();

        incomesCard.setHeader("Доходы");
        expensesCard.setHeader("Расходы");
        totalCard.setHeader("Баланс");

        incomesCard.add(FormattingUtils.formatAmountWithCurrency(incomes));
        expensesCard.add(FormattingUtils.formatAmountWithCurrency(expenses));
        totalCard.add(FormattingUtils.formatAmountWithCurrency(total));
    }
}
