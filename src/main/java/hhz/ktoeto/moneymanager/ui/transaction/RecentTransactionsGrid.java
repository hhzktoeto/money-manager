package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
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
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        root.setAllRowsVisible(true);

        root.addColumn(transaction -> FormattingUtils.formatDate(transaction.getDate()))
                .setKey("date");
        root.addColumn(transaction -> transaction.getCategory().getName())
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        root.addColumn(transaction -> FormattingUtils.formatAmount(transaction.getAmount()) + "₽")
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END);

        root.setDataProvider(dataProvider);

        return root;
    }
}
