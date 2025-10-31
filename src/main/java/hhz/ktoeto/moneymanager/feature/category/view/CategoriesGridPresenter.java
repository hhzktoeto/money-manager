package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.event.CategoryEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.mixin.HasCategoriesProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationEventPublisher;

public abstract class CategoriesGridPresenter implements ViewPresenter, HasCategoriesProvider, CanEdit<Category> {

    private final CategoryDataProvider dataProvider;
    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;
    private final transient ApplicationEventPublisher eventPublisher;

    protected CategoriesGridPresenter(UserContextHolder userContextHolder, CategoryService categoryService,
                                      CategoryDataProvider dataProvider, ApplicationEventPublisher eventPublisher) {
        this.userContextHolder = userContextHolder;
        this.categoryService = categoryService;
        this.dataProvider = dataProvider;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @PostConstruct
    public abstract void initialize();

    @Override
    public void onEditRequested(Category category) {
        this.eventPublisher.publishEvent(new CategoryEditRequested(this, category));
    }

    @Override
    public CategoryDataProvider getCategoriesProvider() {
        return this.dataProvider;
    }
}
