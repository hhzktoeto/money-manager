package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoriesDataProvider;
import hhz.ktoeto.moneymanager.feature.category.data.EnhancedCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.event.CategoryEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.mixin.HasCategoriesProvider;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

public abstract class CategoriesGridPresenter implements ViewPresenter, HasCategoriesProvider, CanEdit<Category> {

    private final EnhancedCategoriesProvider dataProvider;
    private final transient CategoryService categoryService;
    private final transient UserContextHolder userContextHolder;
    private final transient ApplicationEventPublisher eventPublisher;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private CategoriesGridView view;

    protected CategoriesGridPresenter(UserContextHolder userContextHolder, CategoryService categoryService,
                                      EnhancedCategoriesProvider dataProvider, ApplicationEventPublisher eventPublisher) {
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
    public CategoriesDataProvider getCategoriesProvider() {
        return this.dataProvider;
    }
}
