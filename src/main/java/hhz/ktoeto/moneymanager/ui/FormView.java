package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.mixin.HasEditableEntity;
import hhz.ktoeto.moneymanager.ui.mixin.Resettable;

public interface FormView<T> extends View, HasEditableEntity<T>, Resettable<T> {

    boolean isCreateMode();

    boolean writeToIfValid(T entity);
}
