package hhz.ktoeto.moneymanager.ui.mixin;

@FunctionalInterface
public interface CanEdit<T> {

    void onEditRequested(T entity);
}
