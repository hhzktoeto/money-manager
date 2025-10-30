package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;
import java.math.BigDecimal;

@FunctionalInterface
public interface CanFormatAmount extends Serializable {

    String formatAmount(BigDecimal amount);
}
