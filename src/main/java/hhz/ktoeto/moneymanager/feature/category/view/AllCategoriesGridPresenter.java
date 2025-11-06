package hhz.ktoeto.moneymanager.feature.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.EnhancedCategoriesProvider;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class AllCategoriesGridPresenter extends CategoriesGridPresenter {

    protected AllCategoriesGridPresenter(EnhancedCategoriesProvider dataProvider, ApplicationEventPublisher eventPublisher,
                                         FormattingService formattingService) {
        super(dataProvider, eventPublisher, formattingService);
    }

    @Override
    public void initialize() {
        this.setView(new AllCategoriesGridView(this));
    }
}
