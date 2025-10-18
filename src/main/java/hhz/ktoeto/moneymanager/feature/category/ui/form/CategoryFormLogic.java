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
public class CategoryFormLogic {

    private final CategoryService categoryService;
    private final UserContextHolder userContextHolder;
    private final ApplicationEventPublisher eventPublisher;

    public void submitCreate(CategoryForm form) {
        long userId = userContextHolder.getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = form.writeTo(category);
        if (!valid) {
            return;
        }

        if (categoryService.exist(form.name().trim(), userId)) {
            form.getNameField().setErrorMessage("Категория с таким именем уже существует");
            form.getNameField().setInvalid(true);
            return;
        }

        Category createdCategory = categoryService.create(category);
        eventPublisher.publishEvent(new CategoryCreatedEvent(this, createdCategory));

        form.getNameField().clear();
    }

    public void cancelCreation() {
        eventPublisher.publishEvent(new CategoryCreationCancelledEvent(this));
    }
}
