package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.ui.data.TransactionDataProvider;
import hhz.ktoeto.moneymanager.ui.component.NoTransactionsImage;
import hhz.ktoeto.moneymanager.feature.transaction.event.OpenTransactionEditDialogEvent;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class RecentTransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient ApplicationEventPublisher eventPublisher;
    private final transient TransactionDataProvider dataProvider;

    public RecentTransactionsGrid(ApplicationEventPublisher eventPublisher,
                                  @Qualifier("recentTransactionsProvider") TransactionDataProvider dataProvider) {
        this.eventPublisher = eventPublisher;
        this.dataProvider = dataProvider;
    }

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        root.setAllRowsVisible(true);
        NoTransactionsImage noTransactionsImage = new NoTransactionsImage();
        noTransactionsImage.setText("Нет недавних транзакций");
        root.setEmptyStateComponent(noTransactionsImage);

        root.addColumn(transaction -> FormattingUtils.formatDate(transaction.getDate()))
                .setKey("date");
        root.addColumn(transaction -> transaction.getCategory().getName())
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        root.addColumn(transaction -> FormattingUtils.formatAmount(transaction.getAmount()))
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END);

        root.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(transaction ->
            eventPublisher.publishEvent(new OpenTransactionEditDialogEvent(this, transaction))
        ));

        root.setDataProvider(dataProvider);

        return root;
    }
}
