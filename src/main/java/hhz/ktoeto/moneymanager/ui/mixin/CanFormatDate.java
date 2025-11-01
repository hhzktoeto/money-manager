package hhz.ktoeto.moneymanager.ui.mixin;

import java.io.Serializable;
import java.time.LocalDate;

@FunctionalInterface
public interface CanFormatDate extends Serializable {

    String formatDate(LocalDate date);
}
