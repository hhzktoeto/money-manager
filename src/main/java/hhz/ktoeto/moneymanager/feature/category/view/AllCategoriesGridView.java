package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;

import java.util.Collections;

public class AllCategoriesGridView extends CategoriesGridView {

    protected AllCategoriesGridView(CategoriesGridPresenter presenter) {
        super(presenter);
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = super.initContent();

        this.configureGridHeader();

        return root;
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет категорий";
    }

    @Override
    protected boolean isSortable() {
        return true;
    }

    @Override
    protected void configurePagination(Grid<Category> grid) {
        grid.setPageSize(10);
    }

    private void configureGridHeader() {
        Grid.Column<Category> categoryNameColumn = this.getRootGrid().getColumnByKey("name")
                .setHeader("По имени");

        this.getRootGrid().sort(Collections.singletonList(
                new GridSortOrder<>(categoryNameColumn, SortDirection.ASCENDING)
        ));
    }
}
