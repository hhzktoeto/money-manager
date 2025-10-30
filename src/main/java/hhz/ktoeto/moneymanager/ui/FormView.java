package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.mixin.HasEntity;

public interface FormView<T> extends View, HasEntity<T> {

    boolean writeToIfValid(T entity);
}
