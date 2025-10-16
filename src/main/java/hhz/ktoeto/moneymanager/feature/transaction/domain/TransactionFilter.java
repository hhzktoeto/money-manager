package hhz.ktoeto.moneymanager.feature.transaction.domain;

import lombok.Data;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Data
public class TransactionFilter {

    private LocalDate fromDate;
    private LocalDate toDate;

    public static TransactionFilter currentMonthFilter() {
        Clock clock = Clock.systemDefaultZone();
        TransactionFilter filter = new TransactionFilter();
        filter.setFromDate(LocalDate.now(clock).with(TemporalAdjusters.firstDayOfMonth()));
        filter.setToDate(LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth()));

        return filter;
    }
}
