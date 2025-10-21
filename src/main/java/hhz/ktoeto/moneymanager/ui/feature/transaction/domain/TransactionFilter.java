package hhz.ktoeto.moneymanager.ui.feature.transaction.domain;

import lombok.Data;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

@Data
public class TransactionFilter {

    private LocalDate fromDate;
    private LocalDate toDate;
    private Set<Long> categoriesIds;
    private Transaction.Type type;

    public static TransactionFilter currentMonthFilter() {
        Clock clock = Clock.systemDefaultZone();
        TransactionFilter filter = new TransactionFilter();
        filter.setFromDate(LocalDate.now(clock).with(TemporalAdjusters.firstDayOfMonth()));
        filter.setToDate(LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth()));

        return filter;
    }
}
