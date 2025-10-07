package hhz.ktoeto.moneymanager.ui.category;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.transaction.entity.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.util.ArrayList;

@SpringComponent
public class CategoryNameDataProvider extends ListDataProvider<String> {

    private final CategoryService categoryService;

    public CategoryNameDataProvider(CategoryService categoryService) {
        super(new ArrayList<>());
        this.categoryService = categoryService;

    }

    public void refresh() {
        this.getItems().clear();
        this.getItems().addAll(
                categoryService.getAll(SecurityUtils.getCurrentUser().getId())
                        .stream()
                        .map(Category::getName)
                        .sorted(String.CASE_INSENSITIVE_ORDER)
                        .toList()
        );
        this.refreshAll();
    }
}
