package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ClickableRenderer;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@UIScope
@Component
public class SummaryCards extends HorizontalLayout {

    private final TransactionService transactionService;

    private final Card incomesCard;
    private final Card expensesCard;
    private final Card totalCard;

    public SummaryCards(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.incomesCard = new Card();
        this.expensesCard = new Card();
        this.totalCard = new Card();

        incomesCard.setHeader(new H1("Доходы"));
        expensesCard.setHeader(new H1("Расходы"));
        totalCard.setHeader(new H1("Баланс"));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        Long userId = SecurityUtils.getCurrentUser().getId();
        List<Transaction> transactions = transactionService.getAll(userId);
        Double incomes = transactions.stream()
                .filter(transaction -> Objects.equals(Transaction.Type.INCOME, transaction.getType()))
                .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                .sum();
        Double expenses = transactions.stream()
                .filter(transaction -> Objects.equals(Transaction.Type.EXPENSE, transaction.getType()))
                .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                .sum();
        double total = incomes - expenses;

        this.incomesCard.removeAll();
        this.expensesCard.removeAll();
        this.totalCard.removeAll();

        this.incomesCard.add(new Span(incomes.toString()));
        this.expensesCard.add(new Span(expenses.toString()));
        this.totalCard.add(new Span(Double.toString(total)));

        add(incomesCard, expensesCard, totalCard);
    }
}
