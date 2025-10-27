package hhz.ktoeto.moneymanager.ui;

@FunctionalInterface
public interface CanEdit<T> {

    void onEditRequested(T entity);
}
