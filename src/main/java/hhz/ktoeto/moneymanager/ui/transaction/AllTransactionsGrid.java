package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import org.springframework.beans.factory.annotation.Qualifier;

@UIScope
@SpringComponent
public class AllTransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient TransactionDataProvider dataProvider;

    public AllTransactionsGrid(@Qualifier("allTransactionsProvider") TransactionDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        root.setAllRowsVisible(true);

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
