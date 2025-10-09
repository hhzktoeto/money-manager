package hhz.ktoeto.moneymanager.ui.category.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.category.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.ui.category.event.CategoryCreationCancelledEvent;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class CategoryCreateFormLogic implements CategoryFormLogic {

    private final CategoryService categoryService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(CategoryForm form) {
        long userId = SecurityUtils.getCurrentUserId();

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
