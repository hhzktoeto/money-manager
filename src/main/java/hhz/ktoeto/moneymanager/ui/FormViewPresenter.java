package hhz.ktoeto.moneymanager.ui;

public interface FormViewPresenter<T, V extends FormView<T>> {

    void setView(V view);

    void openCreateForm();

    void openEditForm(T entity);

    void onSubmit();

    void onCancel();

    void onDelete();
}
