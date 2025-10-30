package hhz.ktoeto.moneymanager.ui.mixin;

@FunctionalInterface
public interface CanAddToFavourite<T> {

    void onAddToFavourites(T entity);
}
