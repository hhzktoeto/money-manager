package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class RecentTransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient RecentTransactionsDataProvider dataProvider;

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.setSizeFull();
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        root.addColumn(transaction -> FormattingUtils.formatDate(transaction.getDate()))
                .setHeader("Дата")
                .setSortable(true)
                .setKey("date");
        root.addColumn(transaction -> transaction.getCategory().getName())
                .setHeader("Категория")
                .setSortable(true)
                .setKey("category");
        root.addColumn(transaction -> FormattingUtils.formatAmount(transaction.getAmount()))
                .setHeader("Сумма")
                .setSortable(true)
                .setKey("amount");

        root.setDataProvider(dataProvider);

        return root;
    }
}
