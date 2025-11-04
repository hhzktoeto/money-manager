package hhz.ktoeto.moneymanager.feature.category.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryFilter;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;

import java.util.List;

@SpringComponent
@VaadinSessionScope
public class EnhancedCategoriesProvider extends CategoriesDataProvider {

    protected EnhancedCategoriesProvider(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    protected void loadData(long userId) {
        CategoryFilter filter = new CategoryFilter();
        filter.setWithGoals(true);
        filter.setWithBudgets(true);
        filter.setWithTransactions(true);

        List<Category> categories = this.getCategoryService().getAll(userId, filter);
        this.getItems().addAll(categories);
    }
}
