package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.grid.Grid;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;

public class RecentTransactionsGrid extends TransactionsGridView {

    public RecentTransactionsGrid(RecentTransactionsGridPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет недавних транзакций";
    }

    @Override
    protected boolean isSortable() {
        return false;
    }

    @Override
    protected void configurePagination(Grid<Transaction> grid) {
        this.grid.setAllRowsVisible(true);
    }
}
