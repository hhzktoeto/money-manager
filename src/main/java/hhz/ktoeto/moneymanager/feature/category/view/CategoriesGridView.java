package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class CategoriesGridView extends Composite<VerticalLayout> implements View {

    private final transient CategoriesGridPresenter presenter;

    @Getter(AccessLevel.PROTECTED)
    private final Grid<Category> rootGrid;

    protected CategoriesGridView(CategoriesGridPresenter presenter) {
        this.presenter = presenter;

        this.rootGrid = new Grid<>();
    }

    protected abstract String getEmptyStateText();

    protected abstract boolean isSortable();

    protected abstract void configurePagination(Grid<Category> grid);

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

        this.rootGrid.addColumn(Category::getName)
                .setKey("name")
                .setSortable(this.isSortable());
    }
}
