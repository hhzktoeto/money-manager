package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.mixin.CanCancel;
import hhz.ktoeto.moneymanager.ui.mixin.CanDelete;
import hhz.ktoeto.moneymanager.ui.mixin.CanSubmit;

public interface FormViewPresenter<T, V extends FormView<T>> extends ViewPresenter<V>, CanSubmit, CanDelete, CanCancel {

    void openCreateForm();

    void openEditForm(T entity);
}
