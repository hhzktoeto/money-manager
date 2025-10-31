package hhz.ktoeto.moneymanager.feature.category.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;

import java.util.List;

@SpringComponent
@VaadinSessionScope
public class AllCategoriesProvider extends CategoriesDataProvider {

    public AllCategoriesProvider(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    protected void loadData() {
        List<Category> categories;
    }
}
