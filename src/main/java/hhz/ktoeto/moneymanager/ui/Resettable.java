package hhz.ktoeto.moneymanager.ui;

@FunctionalInterface
public interface Resettable<T> {

    void reset(T entity);
}
