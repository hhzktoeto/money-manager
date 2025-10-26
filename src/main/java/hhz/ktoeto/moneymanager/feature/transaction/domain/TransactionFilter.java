package hhz.ktoeto.moneymanager.feature.transaction.domain;

import lombok.Data;
import org.springframework.data.domain.Sort;

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
    private String sortField;
    private Sort.Direction sortDirection;

    public static TransactionFilter currentMonthFilter() {
        Clock clock = Clock.systemDefaultZone();
        TransactionFilter filter = new TransactionFilter();
        filter.setFromDate(LocalDate.now(clock).with(TemporalAdjusters.firstDayOfMonth()));
        filter.setToDate(LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth()));
        filter.setSortField("date");
        filter.setSortDirection(Sort.Direction.DESC);

        return filter;
    }
}
