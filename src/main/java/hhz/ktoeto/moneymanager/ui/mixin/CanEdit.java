package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanEdit<T> extends Serializable {

    void onEditRequested(T entity);
}
