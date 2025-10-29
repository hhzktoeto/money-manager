package hhz.ktoeto.moneymanager.ui.mixin;

import java.math.BigDecimal;

@FunctionalInterface
public interface CanFormatAmount {

    String formatAmount(BigDecimal amount);
}
