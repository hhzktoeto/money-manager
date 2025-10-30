package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanAddToFavourite<T> extends Serializable {

    void onAddToFavourites(T entity);
}
