package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

public interface HasFilter<T> extends Serializable {

    T getFilter();

    void setFilter(T filter);
}
