package hhz.ktoeto.moneymanager.ui;

public interface HasEditableEntity<T> {

    void setEditedEntity(T editedEntity);

    T getEditedEntity();
}
