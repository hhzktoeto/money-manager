package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllCategoriesGridView extends CategoriesGridView {

    protected AllCategoriesGridView(CategoriesGridPresenter presenter) {
        super(presenter);
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = super.initContent();

        this.configureSortingAndDetails();

        return root;
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет категорий";
    }

    @Override
    protected void configurePagination(Grid<Category> grid) {
        grid.setPageSize(25);
    }

    private void configureSortingAndDetails() {
        Grid<Category> grid = this.getRootGrid();

        Grid.Column<Category> detailsOpenColumn = grid.addColumn(new CategoryDetailsOpenerRenderer(grid))
                .setWidth("2rem")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Category> nameColumn = grid.getColumnByKey("name")
                .setHeader("По имени")
                .setSortable(true);
        Grid.Column<Category> categoryTransactionsCountColumn = grid.getColumnByKey("transactions.count")
                .setHeader("По популярности")
                .setSortable(true)
                .setComparator(Comparator.comparingInt(Category::getTransactionsCount));

        grid.sort(Collections.singletonList(
                new GridSortOrder<>(categoryTransactionsCountColumn, SortDirection.DESCENDING)
        ));
        grid.setColumnOrder(List.of(detailsOpenColumn, nameColumn, categoryTransactionsCountColumn));

        grid.setItemDetailsRenderer(new CategoryDetailsRenderer(this.getPresenter()::formatAmount));
        grid.setDetailsVisibleOnClick(false);
    }

    private static final class CategoryDetailsOpenerRenderer extends ComponentRenderer<Button, Category> {

        public CategoryDetailsOpenerRenderer(Grid<Category> grid) {
            super(category -> {
                Button button = new Button();
                button.setTooltipText("Подробнее");
                button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_CONTRAST);
                button.addAttachListener(event -> {
                    boolean isVisible = grid.isDetailsVisible(category);

                    button.setIcon(isVisible
                            ? MaterialIcons.KEYBOARD_ARROW_DOWN.create()
                            : MaterialIcons.KEYBOARD_ARROW_RIGHT.create()
                    );
                });
                button.addClickListener(event -> {
                    boolean newVisible = !grid.isDetailsVisible(category);

                    grid.setDetailsVisible(category, newVisible);
                    button.setIcon(newVisible
                            ? MaterialIcons.KEYBOARD_ARROW_DOWN.create()
                            : MaterialIcons.KEYBOARD_ARROW_RIGHT.create()
                    );
                });

                return button;
            });
        }
    }

    private static final class CategoryDetailsRenderer extends ComponentRenderer<FlexLayout, Category> {

        public CategoryDetailsRenderer(SerializableFunction<BigDecimal, String> amountFormatter) {
            super(category -> {
                FlexLayout layout = new FlexLayout();
                layout.addClassNames(
                        LumoUtility.Padding.Left.XLARGE,
                        LumoUtility.Padding.Top.XSMALL,
                        LumoUtility.Padding.Bottom.SMALL,
                        LumoUtility.Width.FULL
                );

                VerticalLayout statsContainer = new VerticalLayout();
                statsContainer.setPadding(false);
                statsContainer.addClassNames(
                        LumoUtility.Width.FULL,
                        LumoUtility.Gap.SMALL
                );

                if (category.getExpenseTransactionsCount() > 0) {
                    statsContainer.add(createStatsCard(
                            "Расходные транзакции",
                            category.getExpenseTransactionsCount(),
                            amountFormatter.apply(category.getExpenseTransactionsAmount()),
                            LumoUtility.TextColor.ERROR,
                            MaterialIcons.TRENDING_DOWN
                    ));
                }
                if (category.getIncomeTransactionsCount() > 0) {
                    statsContainer.add(createStatsCard(
                            "Доходные транзакции",
                            category.getIncomeTransactionsCount(),
                            amountFormatter.apply(category.getIncomeTransactionsAmount()),
                            LumoUtility.TextColor.SUCCESS,
                            MaterialIcons.TRENDING_UP
                    ));
                }
                if (category.getTransactionsCount() == 0 && category.getIncomeTransactionsCount() == 0) {
                    Span noTransactions = new Span("Нет транзакций");
                    noTransactions.addClassNames(
                            LumoUtility.TextColor.DISABLED
                    );
                    statsContainer.add(noTransactions);
                }

                layout.add(statsContainer);
                return layout;
            });
        }

        private static VerticalLayout createStatsCard(String title, int count, String amountText, String colorClass, MaterialIcons icon) {
            VerticalLayout card = new VerticalLayout();
            card.setPadding(false);
            card.addClassNames(
                    LumoUtility.FlexDirection.COLUMN,
                    LumoUtility.Gap.XSMALL
            );

            Icon vaadinIcon = icon.create();
            vaadinIcon.addClassNames(colorClass, LumoUtility.FontSize.SMALL);

            Span titleSpan = new Span(title);
            titleSpan.addClassNames(LumoUtility.FontSize.MEDIUM, LumoUtility.FontWeight.SEMIBOLD);

            FlexLayout iconTitleContainer = new FlexLayout(vaadinIcon, titleSpan);
            iconTitleContainer.addClassNames(
                    LumoUtility.FlexDirection.ROW,
                    LumoUtility.Gap.SMALL
            );
            card.add(iconTitleContainer);

            String transactionsText = count % 100 >= 11 && count % 100 <= 19
                    ? "транзакций"
                    : switch (count % 10) {
                case 1 -> "транзакция";
                case 2, 3, 4 -> "транзакции";
                default -> "транзакций";
            };
            Span countSpan = new Span(count + " " + transactionsText);
            countSpan.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
            card.add(countSpan);

            Span amountSpan = new Span(amountText);
            amountSpan.addClassNames(LumoUtility.FontSize.LARGE, colorClass);
            card.add(amountSpan);

            return card;
        }
    }
}
