package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionsSummaries;
import org.springframework.beans.factory.annotation.Qualifier;

@UIScope
@SpringComponent
public class RecentTransactionsGrid extends AbstractTransactionsGrid {

    public RecentTransactionsGrid(@Qualifier("recentTransactionsPresenter") TransactionsGridViewPresenter presenter) {
        super(presenter);
        presenter.initialize(this);
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

    @Override
    public void updateSummaries(TransactionsSummaries summaries) {
        throw new UnsupportedOperationException("updateSummaries() should not be called on RecentTransactionsGrid");
    }
}
