package hhz.ktoeto.moneymanager.feature.statistics.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.event.*;
import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.statistics.domain.StatisticsService;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
@RequiredArgsConstructor
public class CategorySumProvider extends AbstractBackEndDataProvider<CategorySum, Void> {

    private final transient UserContextHolder userContextHolder;
    private final transient StatisticsService statisticsService;

    public List<CategorySum> getCategorySums(LocalDate from, LocalDate to, Transaction.Type type) {
        long userId = userContextHolder.getCurrentUserId();

        return statisticsService.getCategorySums(userId, from, to, type);
    }

    @Override
    protected Stream<CategorySum> fetchFromBackEnd(Query<CategorySum, Void> query) {
        // Unused
        return Stream.empty();
    }

    @Override
    protected int sizeInBackEnd(Query<CategorySum, Void> query) {
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
