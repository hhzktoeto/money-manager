package hhz.ktoeto.moneymanager.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public final class DateUtils {

    private DateUtils() {
    }

    public static LocalDate currentMonthStart() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate currentMonthEnd() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }
}
