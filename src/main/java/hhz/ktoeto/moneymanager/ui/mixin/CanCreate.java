package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanCreate extends Serializable {

    void onCreateRequested();
}
