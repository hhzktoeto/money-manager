package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface HasCustomErrors extends Serializable {

    void setError(String error);
}
