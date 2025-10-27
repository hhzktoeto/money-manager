package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;

@FunctionalInterface
public interface HasCategoriesProvider {

    ListDataProvider<Category> getCategoriesProvider();
}
