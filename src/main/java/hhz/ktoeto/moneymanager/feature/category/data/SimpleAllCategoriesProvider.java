package hhz.ktoeto.moneymanager.feature.category.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;

import java.util.Comparator;

@SpringComponent
@VaadinSessionScope
public class SimpleAllCategoriesProvider extends CategoriesDataProvider {

    public SimpleAllCategoriesProvider(CategoryService categoryService, UserContextHolder userContextHolder) {
        super(categoryService, userContextHolder);
    }

    @Override
    protected void loadData(long userId) {
        this.getItems().addAll(
                this.getCategoryService().getAll(userId)
                        .stream()
                        .sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
                        .toList()
        );
    }
}
