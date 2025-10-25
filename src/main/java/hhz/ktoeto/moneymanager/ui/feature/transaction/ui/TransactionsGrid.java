package hhz.ktoeto.moneymanager.ui.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.event.OpenTransactionEditDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.data.TransactionDataProvider;
import lombok.Builder;
import org.springframework.context.ApplicationEventPublisher;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Builder
public class TransactionsGrid extends Composite<Grid<Transaction>> {

    private final transient TransactionDataProvider dataProvider;
    private final transient ApplicationEventPublisher eventPublisher;

    private final Mode mode;

    public enum Mode {
        RECENT,
        ALL
    }

    @Override
    protected Grid<Transaction> initContent() {
        Grid<Transaction> root = new Grid<>();
        root.addClassNames(LumoUtility.Background.TRANSPARENT);
        root.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        if (mode == Mode.RECENT) {
            root.setAllRowsVisible(true);
        } else {
            root.setPageSize(25);
        }
        root.setSelectionMode(Grid.SelectionMode.NONE);

        EmptyDataImage noTransactionsImage = new EmptyDataImage();
        noTransactionsImage.setText(mode == Mode.RECENT
                ? "Нет недавних транзакций"
                : "Нет транзакций за выбранный период");
        root.setEmptyStateComponent(noTransactionsImage);

        root.setDataProvider(dataProvider);
        root.addItemClickListener(event ->
                eventPublisher.publishEvent(new OpenTransactionEditDialogEvent(this, event.getItem()))
        );
        root.addColumn(new TransactionCategoryDateRenderer());
        root.addColumn(new TransactionAmountRenderer())
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
        return root;
    }

    public TransactionFilter getCurrentFilter() {
        return dataProvider.getCurrentFilter();
    }

    public void setCurrentFilter(TransactionFilter filter) {
        dataProvider.setCurrentFilter(filter);
    }

    private static final class TransactionAmountRenderer extends NumberRenderer<Transaction> {

        private TransactionAmountRenderer() {
            super(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault()));
        }
    }

    private static final class TransactionCategoryDateRenderer extends ComponentRenderer<HorizontalLayout, Transaction> {

        //TODO: Добавить иконки для категория, когда они появятся
        private TransactionCategoryDateRenderer() {
            super(transaction -> {
                HorizontalLayout layout = new HorizontalLayout();
                Category transactionCategory = transaction.getCategory();
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("dd MMMM yyyy")
                        .toFormatter()
                        .withLocale(Locale.of("ru"));

                Span categoryName = new Span(transactionCategory.getName());
                categoryName.addClassNames(
                        LumoUtility.FontWeight.BOLD,
                        LumoUtility.FontSize.MEDIUM
                );
                Span transactionDate = new Span(formatter.format(transaction.getDate()));
                transactionDate.addClassNames(
                        LumoUtility.TextColor.SECONDARY,
                        LumoUtility.FontWeight.LIGHT,
                        LumoUtility.FontSize.SMALL
                );

                VerticalLayout nameDateLayout = new VerticalLayout(categoryName, transactionDate);
                nameDateLayout.addClassName(LumoUtility.Gap.XSMALL);

                layout.add(nameDateLayout);
                return layout;
            });
        }
    }
}
