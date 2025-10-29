package hhz.ktoeto.moneymanager.ui.mixin;

public interface HasFilter<T> {

    T getFilter();

    void setFilter(T filter);
}
