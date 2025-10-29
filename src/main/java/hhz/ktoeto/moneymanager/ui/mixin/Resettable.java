package hhz.ktoeto.moneymanager.ui.mixin;

@FunctionalInterface
public interface Resettable<T> {

    void reset(T entity);
}
