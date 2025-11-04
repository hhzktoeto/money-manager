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
    protected void configurePagination(Grid<Category> grid) {
        grid.setPageSize(10);
    }

    private void configureGridHeader() {
        Grid<Category> grid = this.getRootGrid();

        grid.getColumnByKey("name")
                .setHeader("По имени")
                .setSortable(true);
        Grid.Column<Category> categoryTransactionsCountColumn = grid.getColumnByKey("transactions.count")
                .setHeader("По популярности")
                .setSortable(true);

        grid.sort(Collections.singletonList(
                new GridSortOrder<>(categoryTransactionsCountColumn, SortDirection.DESCENDING)
        ));
    }
}
