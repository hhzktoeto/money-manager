package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.broadcast.Broadcaster;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionDeletedEvent;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionUpdatedEvent;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
public class SummaryCards extends HorizontalLayout {

    private final transient TransactionService transactionService;
    private final transient Broadcaster broadcaster;

    private final Card incomesCard = new Card();
    private final Card expensesCard = new Card();
    private final Card totalCard = new Card();

    public SummaryCards(TransactionService transactionService, Broadcaster broadcaster) {
        this.transactionService = transactionService;
        this.broadcaster = broadcaster;

        incomesCard.setHeader(new H1("Доходы"));
        expensesCard.setHeader(new H1("Расходы"));
        totalCard.setHeader(new H1("Баланс"));

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

        incomesCard.add(new Span(incomes.toPlainString()));
        expensesCard.add(new Span(expenses.toPlainString()));
        totalCard.add(new Span(total.toPlainString()));
    }
}
