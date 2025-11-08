package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.category.data.CategoriesDataProvider;
import hhz.ktoeto.moneymanager.feature.category.data.EnhancedCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.event.CategoryEditRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanCreate;
import hhz.ktoeto.moneymanager.ui.mixin.CanEdit;
import hhz.ktoeto.moneymanager.ui.mixin.CanFormatAmount;
import hhz.ktoeto.moneymanager.ui.mixin.HasCategoriesProvider;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

public abstract class CategoriesGridPresenter implements ViewPresenter, HasCategoriesProvider, CanEdit<Category>, CanCreate, CanFormatAmount {

    private final EnhancedCategoriesProvider dataProvider;
    private final transient FormattingService formattingService;
    private final transient ApplicationEventPublisher eventPublisher;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private CategoriesGridView view;

    protected CategoriesGridPresenter(EnhancedCategoriesProvider dataProvider, ApplicationEventPublisher eventPublisher,
                                      FormattingService formattingService) {
        this.dataProvider = dataProvider;
        this.eventPublisher = eventPublisher;
        this.formattingService = formattingService;
    }

    @Override
    @PostConstruct
    public abstract void initialize();

    @Override
    public String formatAmount(BigDecimal amount) {
        return this.formattingService.formatAmount(amount);
    }

    @Override
    public void onEditRequested(Category category) {
        this.eventPublisher.publishEvent(new CategoryEditRequested(this, category));
    }

    @Override
    public void onCreateRequested() {
        this.eventPublisher.publishEvent(new CategoryCreateRequested(this));
    }

    @Override
    public CategoriesDataProvider getCategoriesProvider() {
        return this.dataProvider;
    }
}
