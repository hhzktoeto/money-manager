package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.component.NoTransactionsImage;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.event.OpenTransactionEditDialogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;


@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient FormattingService formattingService;
    private final transient TransactionDataProvider dataProvider;
    private final transient ApplicationEventPublisher eventPublisher;

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        root.setAllRowsVisible(true);
        root.setSelectionMode(Grid.SelectionMode.NONE);

        NoTransactionsImage noTransactionsImage = new NoTransactionsImage();
        noTransactionsImage.setText("Нет транзакций за выбранный период");
        root.setEmptyStateComponent(noTransactionsImage);

        root.addColumn(transaction -> formattingService.formatDate(transaction.getDate()))
                .setHeader("Дата")
                .setSortable(true)
                .setKey("date");
        root.addColumn(transaction -> transaction.getCategory().getName())
                .setHeader("Категория")
                .setSortable(true)
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        root.addColumn(transaction -> formattingService.formatAmount(transaction.getAmount()))
                .setHeader("Сумма")
                .setSortable(true)
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END);

        root.setDataProvider(dataProvider);
        root.addItemClickListener(event ->
                eventPublisher.publishEvent(new OpenTransactionEditDialogEvent(this, event.getItem()))
        );

        return root;
    }

    public TransactionFilter getCurrentFilter() {
        return dataProvider.getCurrentFilter();
    }

    public void setCurrentFilter(TransactionFilter filter) {
        dataProvider.setCurrentFilter(filter);
    }
}
