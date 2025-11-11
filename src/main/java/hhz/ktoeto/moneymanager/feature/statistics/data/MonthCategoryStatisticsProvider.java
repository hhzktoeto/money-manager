package hhz.ktoeto.moneymanager.feature.statistics.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.Statistics;
import hhz.ktoeto.moneymanager.feature.statistics.domain.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class MonthCategoryStatisticsProvider extends AbstractBackEndDataProvider<Statistics, Void> {

    private final transient UserContextHolder userContextHolder;
    private final transient StatisticsService statisticsService;

    public Statistics getStatistics(YearMonth yearMonth) {
        long userId = userContextHolder.getCurrentUserId();

        return statisticsService.getMonthCategoryStatistics(userId, yearMonth);
    }

    public List<YearMonth> getAvailableYearMonths() {
        long userId = userContextHolder.getCurrentUserId();

        return statisticsService.getTransactionsYearMonths(userId);
    }

    @Override
    protected Stream<Statistics> fetchFromBackEnd(Query<Statistics, Void> query) {
        // Unused
        return Stream.empty();
    }

    @Override
    protected int sizeInBackEnd(Query<Statistics, Void> query) {
        // Unused
        return 0;
    }

    @EventListener({
            CategoryCreatedEvent.class,
            CategoryUpdatedEvent.class,
            CategoryDeletedEvent.class,
            TransactionCreatedEvent.class,
            TransactionDeletedEvent.class,
            TransactionUpdatedEvent.class
    })
    private void onAnyUpdate() {
        this.refreshAll();
    }
}
