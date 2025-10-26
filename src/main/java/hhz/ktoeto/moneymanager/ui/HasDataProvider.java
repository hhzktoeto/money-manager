package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.data.provider.DataProvider;

public interface HasDataProvider<T extends DataProvider<?, ?>> {

    T getDataProvider();
}
