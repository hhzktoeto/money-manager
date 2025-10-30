package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface HasUpdatableData<T> extends Serializable {

    void update(T data);
}
