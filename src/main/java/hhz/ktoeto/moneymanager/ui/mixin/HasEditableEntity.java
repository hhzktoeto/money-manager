package hhz.ktoeto.moneymanager.ui.mixin;

public interface HasEditableEntity<T> {

    void setEditedEntity(T editedEntity);

    T getEditedEntity();
}
