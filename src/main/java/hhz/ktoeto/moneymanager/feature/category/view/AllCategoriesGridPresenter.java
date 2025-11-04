package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.EnhancedCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.CategoryService;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class AllCategoriesGridPresenter extends CategoriesGridPresenter {

    protected AllCategoriesGridPresenter(UserContextHolder userContextHolder, CategoryService categoryService,
                                         EnhancedCategoriesProvider dataProvider, ApplicationEventPublisher eventPublisher) {
        super(userContextHolder, categoryService, dataProvider, eventPublisher);
    }

    @Override
    public void initialize() {
        this.setView(new AllCategoriesGridView(this));
    }
}
