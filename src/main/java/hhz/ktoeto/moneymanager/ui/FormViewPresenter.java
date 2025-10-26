package hhz.ktoeto.moneymanager.ui;

public interface FormViewPresenter<T, V extends FormView<T>> extends ViewPresenter<V>, CanSubmit, CanDelete, CanCancel {

    void openCreateForm();

    void openEditForm(T entity);
}
