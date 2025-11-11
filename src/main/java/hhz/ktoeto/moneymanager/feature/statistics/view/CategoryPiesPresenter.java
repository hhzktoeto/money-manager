package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.statistics.data.MonthCategoryStatisticsProvider;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.Statistics;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.List;

@UIScope
@SpringComponent
public class CategoryPiesPresenter implements ViewPresenter, DataProviderListener<Statistics> {

    private final MonthCategoryStatisticsProvider dataProvider;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private CategoryPiesView view;

    private YearMonth selectedYearMonth = YearMonth.now();

    public CategoryPiesPresenter(MonthCategoryStatisticsProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @PostConstruct
    public void initialize() {
        CategoryPiesView expensesPieView = new CategoryPiesView(this);
        this.setView(expensesPieView);

        this.dataProvider.addDataProviderListener(this);
        // Call on init to make charts visible without updating
        this.onDataChange(null);
    }

    public List<YearMonth> getAvailableYearMonths() {
        return this.dataProvider.getAvailableYearMonths();
    }

    public void setSelectedYearMonth(YearMonth selectedYearMonth) {
        this.selectedYearMonth = selectedYearMonth;
        this.onDataChange(null);
    }

    @Override
    public void onDataChange(DataChangeEvent<Statistics> event) {
        Statistics statistics = this.dataProvider.getStatistics(this.selectedYearMonth);

        this.view.update(statistics);
    }
}
