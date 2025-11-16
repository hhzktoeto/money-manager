package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.statistics.data.StatisticsDataProvider;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@UIScope
@SpringComponent
public class CategoryDonutPresenter implements ViewPresenter, DataProviderListener<Object> {

    private final StatisticsDataProvider dataProvider;
    @Getter
    private CategoryDonutView view;

    @Getter
    private LocalDate fromDate;
    @Getter
    private LocalDate toDate;

    public CategoryDonutPresenter(StatisticsDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @PostConstruct
    public void initialize() {
        this.view = new CategoryDonutView(this);

        this.fromDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        this.toDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        this.dataProvider.addDataProviderListener(this);
        // Call on init to make charts visible without updating
        this.onDataChange(null);
    }

    public void setDates(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.onDataChange(null);
    }

    @Override
    public void onDataChange(DataChangeEvent<Object> event) {
        List<CategorySum> categorySums = this.dataProvider.getCategorySums(
                this.fromDate,
                this.toDate,
                this.view.getSelectedTransactionType()
        );

        this.view.update(categorySums);
    }
}
