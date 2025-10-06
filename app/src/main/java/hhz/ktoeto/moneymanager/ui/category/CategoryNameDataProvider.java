package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.entity.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@UIScope
@SpringComponent
public class CategoryNameDataProvider extends ListDataProvider<String> {

    public CategoryNameDataProvider(CategoryService categoryService) {
        super(categoryService.getAll(SecurityUtils.getCurrentUser().getId())
                .stream()
                .map(Category::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList()
        );
    }
}
