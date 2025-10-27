package hhz.ktoeto.moneymanager.ui;

import java.math.BigDecimal;

@FunctionalInterface
public interface CanFormatAmount {

    String formatAmount(BigDecimal amount);
}
