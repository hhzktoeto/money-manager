package hhz.ktoeto.moneymanager.feature.category.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryFilter;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.core.event.CategoryCreatedEvent;
import hhz.ktoeto.moneymanager.core.event.CategoryCreationCancelledEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class CategoryFormLogic {

    private final CategoryService categoryService;
    private final UserContextHolder userContextHolder;
    private final ApplicationEventPublisher eventPublisher;

    @Setter
    private CategoryForm form;

    public void submitCreate() {
        long userId = userContextHolder.getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);

        boolean valid = form.writeTo(category);
        if (!valid) {
            return;
        }

        CategoryFilter nameFilter = new CategoryFilter();
        nameFilter.setName(category.getName());

        if (categoryService.exist(userId, nameFilter)) {
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
