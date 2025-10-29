package hhz.ktoeto.moneymanager.feature.transaction.presenter;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridSettingsView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridSettingsViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionsGridSettingsPresenter implements TransactionsGridSettingsViewPresenter {

    private final CategoryDataProvider categoryDataProvider;
    private final TransactionsGridViewPresenter allTransactionsPresenter;

    private TransactionsGridSettingsView view;

    @Override
    public void initialize(TransactionsGridSettingsView view) {
        this.view = view;
    }

    @Override
    public ListDataProvider<Category> getCategoriesProvider() {
        return this.categoryDataProvider;
    }
}
