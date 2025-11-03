package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.grid.Grid;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;

public class RecentTransactionsGridView extends TransactionsGridView {

    public RecentTransactionsGridView(RecentTransactionsGridPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет недавних транзакций";
    }

    @Override
    protected void configurePagination(Grid<Transaction> grid) {
        this.getRootGrid().setAllRowsVisible(true);
    }
}
