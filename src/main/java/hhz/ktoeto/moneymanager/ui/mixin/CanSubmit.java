package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanSubmit extends Serializable {

    void onSubmit();
}
