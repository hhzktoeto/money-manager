package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import lombok.AccessLevel;
import lombok.Getter;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Getter(AccessLevel.PROTECTED)
public abstract class TransactionsGridView extends Composite<VerticalLayout> implements View {

    private final transient TransactionsGridPresenter presenter;

    private final Grid<Transaction> rootGrid;

    protected TransactionsGridView(TransactionsGridPresenter presenter) {
        this.presenter = presenter;

        this.rootGrid = new Grid<>();
    }

    protected abstract String getEmptyStateText();

    protected abstract void configurePagination(Grid<Transaction> grid);

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setPadding(false);
        root.setSpacing(false);
        root.setSizeFull();

        this.configureGrid();

        root.add(this.rootGrid);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureGrid() {
        this.rootGrid.setDataProvider(this.presenter.getTransactionsProvider());
        this.rootGrid.addClassNames(LumoUtility.Background.TRANSPARENT);
        this.rootGrid.addThemeVariants(
                GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS
        );

        this.configurePagination(this.rootGrid);

        this.rootGrid.setSelectionMode(Grid.SelectionMode.NONE);
        this.rootGrid.addItemClickListener(event -> this.presenter.onEditRequested(event.getItem()));

        EmptyDataImage noTransactionsImage = new EmptyDataImage();
        noTransactionsImage.setText(this.getEmptyStateText());
        this.rootGrid.setEmptyStateComponent(noTransactionsImage);

        this.rootGrid.addColumn(new TransactionCategoryDateRenderer())
                .setKey("date");
        this.rootGrid.addColumn(new NumberRenderer<>(Transaction::getAmount, NumberFormat.getCurrencyInstance(Locale.getDefault())))
                .setKey("sum")
                .setTextAlign(ColumnTextAlign.END)
                .setPartNameGenerator(transaction -> {
                    StringBuilder stringBuilder = new StringBuilder("sum-column ");
                    if (transaction.getType() == Transaction.Type.EXPENSE) {
                        stringBuilder.append("expense");
                    } else {
                        stringBuilder.append("income");
                    }
                    return stringBuilder.toString();
                });
    }

    private static final class TransactionCategoryDateRenderer extends ComponentRenderer<HorizontalLayout, Transaction> {

        public TransactionCategoryDateRenderer() {
            super(transaction -> {
                Category transactionCategory = transaction.getCategory();
                Image categoryIcon = new Image("categories/" + transactionCategory.getIconFileName(), "");
                categoryIcon.setWidth(2, Unit.REM);
                categoryIcon.setHeight(2, Unit.REM);

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
                nameDateLayout.setPadding(false);
                nameDateLayout.addClassName(LumoUtility.Gap.XSMALL);

                HorizontalLayout layout = new HorizontalLayout(categoryIcon, nameDateLayout);
                layout.setSpacing(false);
                layout.addClassNames(
                        LumoUtility.Gap.MEDIUM,
                        LumoUtility.Padding.XSMALL,
                        LumoUtility.AlignItems.CENTER);

                return layout;
            });
        }
    }
}
