package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@UIScope
@SpringComponent
public class CategoriesCard extends Card {

    public CategoriesCard(CategoryService categoryService) {
        setWidthFull();
        setHeightFull();

        Grid<Category> categoriesGrid = new Grid<>(Category.class, false);
        categoriesGrid.setWidthFull();
        categoriesGrid.setHeightFull();
        categoriesGrid.addColumn(Category::getName);

        ListDataProvider<Category> dataProvider = DataProvider.ofCollection(
                categoryService.getAll(SecurityUtils.getCurrentUser().getId())
        );

        categoriesGrid.setDataProvider(dataProvider);
        add(categoriesGrid);
    }
}
