package hhz.ktoeto.moneymanager.ui.mixin;

import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;

import java.io.Serializable;

@FunctionalInterface
public interface HasCategoriesProvider extends Serializable {

    ListDataProvider<Category> getCategoriesProvider();
}
