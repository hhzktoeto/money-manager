package hhz.ktoeto.moneymanager.ui.component.layout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.broadcast.Broadcaster;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.component.CustomCard;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@UIScope
@SpringComponent
public class SummaryCards extends HorizontalLayout {

    private final transient TransactionService transactionService;
    private final transient Broadcaster broadcaster;

    private static final class SummaryCard extends CustomCard {
    }

    private final SummaryCard incomesCard = new SummaryCard();
    private final SummaryCard expensesCard = new SummaryCard();
    private final SummaryCard totalCard = new SummaryCard();

    public SummaryCards(TransactionService transactionService, Broadcaster broadcaster) {
        this.transactionService = transactionService;
        this.broadcaster = broadcaster;

        add(incomesCard, expensesCard, totalCard);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        refresh();

        broadcaster.register(TransactionAddedEvent.class, this::onTransactionAdded);
        broadcaster.register(TransactionDeletedEvent.class, this::onTransactionDeleted);
        broadcaster.register(TransactionUpdatedEvent.class, this::onTransactionUpdated);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcaster.unregister(TransactionUpdatedEvent.class, this::onTransactionUpdated);
        broadcaster.unregister(TransactionDeletedEvent.class, this::onTransactionDeleted);
        broadcaster.unregister(TransactionAddedEvent.class, this::onTransactionAdded);

        super.onDetach(detachEvent);
    }

    private void onTransactionAdded(TransactionAddedEvent ignored) {
        refresh();
    }

    private void onTransactionDeleted(TransactionDeletedEvent ignored) {
        refresh();
    }

    private void onTransactionUpdated(TransactionUpdatedEvent ignored) {
        refresh();
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
