package hhz.ktoeto.moneymanager.ui.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.ui.component.NoTransactionsImage;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.event.OpenTransactionEditDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.data.TransactionDataProvider;
import lombok.Builder;
import org.springframework.context.ApplicationEventPublisher;

@Builder
public class TransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient FormattingService formattingService;
    private final transient TransactionDataProvider dataProvider;
    private final transient ApplicationEventPublisher eventPublisher;
    private final Mode mode;

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_WRAP_CELL_CONTENT);
        root.setAllRowsVisible(true);
        root.setSelectionMode(Grid.SelectionMode.NONE);

        NoTransactionsImage noTransactionsImage = new NoTransactionsImage();
        noTransactionsImage.setText(mode == Mode.RECENT
                ? "Нет недавних транзакций"
                : "Нет транзакций за выбранный период");
        root.setEmptyStateComponent(noTransactionsImage);

        root.addColumn(transaction -> formattingService.formatDate(transaction.getDate()))
                .setHeader(mode == Mode.ALL
                        ? "Дата"
                        : null)
                .setSortable(mode == Mode.ALL)
                .setKey("date");
        root.addColumn(transaction -> transaction.getCategory().getName())
                .setHeader(mode == Mode.ALL
                        ? "Категория"
                        : null)
                .setSortable(mode == Mode.ALL)
                .setKey("category")
                .setTextAlign(ColumnTextAlign.CENTER);
        root.addColumn(transaction -> formattingService.formatAmount(transaction.getAmount()))
                .setHeader(mode == Mode.ALL
                        ? "Сумма"
                        : null)
                .setSortable(mode == Mode.ALL)
                .setKey("amount")
                .setTextAlign(ColumnTextAlign.END)
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("amount-column ");
                    switch (transaction.getType()) {
                        case EXPENSE -> stringBuilder.append("expense");
                        case INCOME -> stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });

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

    public enum Mode {
        RECENT,
        ALL
    }
}
