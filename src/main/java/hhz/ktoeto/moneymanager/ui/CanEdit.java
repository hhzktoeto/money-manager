package hhz.ktoeto.moneymanager.ui;

public interface CanEdit<T> {

    void setEditedEntity(T editedEntity);

    T getEditedEntity();
}
