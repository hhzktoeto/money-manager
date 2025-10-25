package hhz.ktoeto.moneymanager.ui;

public interface FormView<T> {

    boolean isCreateMode();

    void setEditedEntity(T editedEntity);

    T getEditedEntity();

    void reset();

    boolean writeToIfValid(T entity);
}
