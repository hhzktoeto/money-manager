package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

public interface HasEntity<T> extends Serializable {

    void setEntity(T entity);

    T getEntity();
}
