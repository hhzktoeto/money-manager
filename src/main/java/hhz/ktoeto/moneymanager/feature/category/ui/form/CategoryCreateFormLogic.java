package hhz.ktoeto.moneymanager.feature.category.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.feature.category.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.feature.category.event.CategoryCreationCancelledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class CategoryCreateFormLogic implements CategoryFormLogic {

    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;
    private final transient ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(CategoryForm form) {
        long userId = userContextHolder.getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = form.writeTo(category);
        if (!valid) {
            return;
        }

        if (categoryService.exist(form.name().trim(), userId)) {
            form.components().nameField().setErrorMessage("Категория с таким именем уже существует");
            form.components().nameField().setInvalid(true);
            return;
        }

        Category createdCategory = categoryService.create(category);
        eventPublisher.publishEvent(new CategoryCreatedEvent(this, createdCategory));

        form.components().nameField().clear();
    }

    @Override
    public void onCancel(CategoryForm form) {
        eventPublisher.publishEvent(new CategoryCreationCancelledEvent(this));
    }
}
