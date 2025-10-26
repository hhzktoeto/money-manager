package hhz.ktoeto.moneymanager.ui;

public interface FormView<T> extends View, CanEdit<T>, Resettable<T> {

    boolean isCreateMode();

    boolean writeToIfValid(T entity);
}
