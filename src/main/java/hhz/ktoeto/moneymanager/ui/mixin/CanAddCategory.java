package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;

@FunctionalInterface
public interface CanAddCategory extends Serializable {

    void onCategoryAdd();
}
