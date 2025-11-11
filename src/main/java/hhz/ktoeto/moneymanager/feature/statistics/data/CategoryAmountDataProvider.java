package hhz.ktoeto.moneymanager.feature.statistics.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.statistics.domain.CategoryAmount;
import hhz.ktoeto.moneymanager.feature.statistics.domain.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.time.YearMonth;
import java.util.Set;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class CategoryAmountDataProvider extends AbstractBackEndDataProvider<CategoryAmount, Void> {

    private final UserContextHolder userContextHolder;
    private final StatisticsService statisticsService;

    public Stream<CategoryAmount> fetch(YearMonth yearMonth) {
        long userId = userContextHolder.getCurrentUserId();
        return statisticsService.getExpensePieData(userId, yearMonth).stream();
    }

    public Set<YearMonth> fetchAvailableYearMonths() {
        long userId = userContextHolder.getCurrentUserId();
        return statisticsService.getTransactionsYearMonths(userId);
    }

    @Override
    protected Stream<CategoryAmount> fetchFromBackEnd(Query<CategoryAmount, Void> query) {
        // Unused
        return Stream.empty();
    }

    @Override
    protected int sizeInBackEnd(Query<CategoryAmount, Void> query) {
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
