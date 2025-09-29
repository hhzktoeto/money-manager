package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
public class SummaryCards extends HorizontalLayout {

    private final transient TransactionService transactionService;


    private final Card incomesCard = new Card();
    private final Card expensesCard = new Card();
    private final Card totalCard = new Card();

    public SummaryCards(TransactionService transactionService) {
        this.transactionService = transactionService;

        incomesCard.setHeader(new H1("Доходы"));
        expensesCard.setHeader(new H1("Расходы"));
        totalCard.setHeader(new H1("Баланс"));

        refresh();
        add(incomesCard, expensesCard, totalCard);
    }

    public void refresh() {
        List<Transaction> transactions = transactionService.getAll(SecurityUtils.getCurrentUser().getId());

        Double incomes = transactions.stream()
                .filter(t -> Objects.equals(Transaction.Type.INCOME, t.getType()))
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        Double expenses = transactions.stream()
                .filter(t -> Objects.equals(Transaction.Type.EXPENSE, t.getType()))
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double total = incomes - expenses;

        incomesCard.removeAll();
        expensesCard.removeAll();
        totalCard.removeAll();

        incomesCard.add(new Span(incomes.toString()));
        expensesCard.add(new Span(expenses.toString()));
        totalCard.add(new Span(Double.toString(total)));
    }
}
