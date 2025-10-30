package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanCancel extends Serializable {

    void onCancel();
}
