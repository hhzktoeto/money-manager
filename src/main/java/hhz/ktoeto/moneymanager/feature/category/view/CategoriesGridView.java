package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.View;

public abstract class CategoriesGridView extends Composite<VerticalLayout> implements View {

    private final transient CategoriesGridPresenter presenter;

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

    //TODO: посмотреть модификаторы доступа для полей абстрактных классов, открыть доступ к полям для наследников через protected getter'ы
    private void configureGrid() {
        this.rootGrid.setDataProvider(this.presenter.getCategoriesProvider());
    }
}
