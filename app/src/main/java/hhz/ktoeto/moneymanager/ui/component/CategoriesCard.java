package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@UIScope
@SpringComponent
public class CategoriesCard extends Card {

    private final transient  CategoryService categoryService;

    public CategoriesCard(CategoryService categoryService) {
        this.categoryService = categoryService;

        setWidthFull();
        setHeightFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        Grid<Category> grid = new Grid<>(Category.class, false);
        grid.setWidthFull();
        grid.setHeightFull();
        grid.addColumn(Category::getName);
        grid.setItems(categoryService.findAll(SecurityUtils.getCurrentUser().getId()));
    }
}
