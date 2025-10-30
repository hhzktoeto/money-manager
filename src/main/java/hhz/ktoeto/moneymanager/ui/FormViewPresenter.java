package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.mixin.CanCancel;
import hhz.ktoeto.moneymanager.ui.mixin.CanDelete;
import hhz.ktoeto.moneymanager.ui.mixin.CanSubmit;

public interface FormViewPresenter<T> extends ViewPresenter, CanSubmit, CanDelete, CanCancel {

    void openForm(T entity);
}
