package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import lombok.AccessLevel;
import lombok.Getter;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

public abstract class CategoriesGridView extends Composite<VerticalLayout> implements View {

    @Getter(AccessLevel.PROTECTED)
    private final transient CategoriesGridPresenter presenter;

    @Getter(AccessLevel.PROTECTED)
    private final Grid<Category> rootGrid;

    private final FlexLayout addNewCategoryButton;

    protected CategoriesGridView(CategoriesGridPresenter presenter) {
        this.presenter = presenter;

        this.rootGrid = new Grid<>();
        this.addNewCategoryButton = new FlexLayout(MaterialIcons.ADD.create(), new Span("Новая категория"));
    }

    protected abstract String getEmptyStateText();

    protected abstract void configurePagination(Grid<Category> grid);

    protected abstract boolean isAddNewCategoryButtonVisible();

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.setPadding(false);
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.Gap.SMALL
        );

        this.configureGrid();

        this.addNewCategoryButton.addClassNames(
                StyleConstants.CLICKABLE,
                LumoUtility.Width.FULL,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Gap.SMALL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Border.ALL,
                LumoUtility.BorderColor.PRIMARY_50,
                LumoUtility.TextColor.PRIMARY,
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.BOLD
        );
        this.addNewCategoryButton.setHeight(2.5f, Unit.REM);
        this.addNewCategoryButton.addClickListener(e -> this.presenter.onCreateRequested());
        this.addNewCategoryButton.setVisible(this.isAddNewCategoryButtonVisible());

        root.add(this.addNewCategoryButton, this.rootGrid);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureGrid() {
        this.rootGrid.setDataProvider(this.presenter.getCategoriesProvider());
        this.rootGrid.addClassNames(LumoUtility.Background.TRANSPARENT);
        this.rootGrid.addThemeVariants(
                GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS
        );

        this.configurePagination(this.rootGrid);

        this.rootGrid.setSelectionMode(Grid.SelectionMode.NONE);
        this.rootGrid.addItemClickListener(event -> this.presenter.onEditRequested(event.getItem()));

        EmptyDataImage noCategoriesImage = new EmptyDataImage();
        noCategoriesImage.setText(this.getEmptyStateText());
        this.rootGrid.setEmptyStateComponent(noCategoriesImage);

        this.rootGrid.addColumn(new CategoryNameIconRenderer())
                .setKey("name")
                .setTextAlign(ColumnTextAlign.START);

        this.rootGrid.addColumn(new CategoryTransactionsCountRenderer())
                .setKey("transactions.count")
                .setTextAlign(ColumnTextAlign.END);
    }

    private static final class CategoryNameIconRenderer extends ComponentRenderer<HorizontalLayout, Category> {

        public CategoryNameIconRenderer() {
            super(category -> {
                Image icon = new Image("categories/" + category.getIconFileName(), "");
                icon.setWidth(2, Unit.REM);
                icon.setHeight(2, Unit.REM);

                Span name = new Span(category.getName());
                name.addClassNames(
                        LumoUtility.FontWeight.BOLD,
                        LumoUtility.FontSize.MEDIUM
                );

                HorizontalLayout layout = new HorizontalLayout(icon, name);
                layout.setSpacing(false);
                layout.setPadding(false);
                layout.addClassNames(
                        LumoUtility.Gap.MEDIUM,
                        LumoUtility.Padding.XSMALL,
                        LumoUtility.AlignItems.CENTER
                );

                return layout;
            });
        }
    }

    private static final class CategoryTransactionsCountRenderer extends ComponentRenderer<HorizontalLayout, Category> {

        public CategoryTransactionsCountRenderer() {
            super(category -> {
                int transactionsCount = category.getTransactionsCount();

                Span countSpan = new Span(Integer.toString(transactionsCount));
                countSpan.addClassNames(LumoUtility.FontWeight.BOLD);

                String transactionsText = transactionsCount % 100 >= 11 && transactionsCount % 100 <= 19
                        ? "транзакций"
                        : switch (transactionsCount % 10) {
                    case 1 -> "транзакция";
                    case 2, 3, 4 -> "транзакции";
                    default -> "транзакций";
                };

                Span textSpan = new Span(transactionsText);
                textSpan.addClassNames(
                        LumoUtility.TextColor.SECONDARY,
                        LumoUtility.FontWeight.LIGHT
                );

                HorizontalLayout layout = new HorizontalLayout(countSpan, textSpan);
                layout.addClassNames(
                        LumoUtility.Gap.SMALL,
                        LumoUtility.JustifyContent.END
                );

                return layout;
            });
        }
    }
}
