package hhz.ktoeto.moneymanager.ui.mixin;

@FunctionalInterface
public interface HasUpdatableData<T> {

    void update(T data);
}
