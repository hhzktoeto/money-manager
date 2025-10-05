package hhz.ktoeto.moneymanager.ui.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.entity.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class CategoryNameToCategoryConverter implements Converter<String, Category> {

    private final CategoryService categoryService;

    @Override
    public Result<Category> convertToModel(String categoryName, ValueContext context) {
        if (categoryName == null || categoryName.isBlank()) {
            return Result.error("Не выбрана категория");
        }
        try {
            long userId = SecurityUtils.getCurrentUser().getId();
            Category category = categoryService.getByNameAndUserId(categoryName, userId)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(categoryName);
                        newCategory.setUserId(userId);
                        return categoryService.create(newCategory);
                    });
            return Result.ok(category);
        } catch (Exception e) {
            return Result.error("Во время создания категория возникла ошибка: " + e.getMessage());
        }
    }

    @Override
    public String convertToPresentation(Category category, ValueContext context) {
        return category != null ? category.getName() : null;
    }
}
