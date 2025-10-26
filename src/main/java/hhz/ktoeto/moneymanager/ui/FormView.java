package hhz.ktoeto.moneymanager.ui;

public interface FormView<T> {

    boolean isCreateMode();

    void setEditedEntity(T editedEntity);

    T getEditedEntity();

    void reset(T resetEntity);

    boolean writeToIfValid(T entity);
}
