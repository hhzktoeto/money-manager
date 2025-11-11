package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.statistics.data.CategoryAmountDataProvider;
import hhz.ktoeto.moneymanager.feature.statistics.domain.CategoryAmount;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.Set;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class ExpensesPiePresenter implements ViewPresenter, DataProviderListener<CategoryAmount> {

    private final CategoryAmountDataProvider dataProvider;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private ExpensesPieView view;

    private YearMonth selectedYearMonth = YearMonth.now();

    public ExpensesPiePresenter(CategoryAmountDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @PostConstruct
    public void initialize() {
        ExpensesPieView expensesPieView = new ExpensesPieView(this);
        this.setView(expensesPieView);

        this.dataProvider.addDataProviderListener(this);
        // Call on init to make charts visible without updating
        this.onDataChange(null);
    }

    public Set<YearMonth> getAvailableYearMonths() {
        return this.dataProvider.fetchAvailableYearMonths();
    }

    public void setSelectedYearMonth(YearMonth selectedYearMonth) {
        this.selectedYearMonth = selectedYearMonth;
        this.onDataChange(null);
    }

    @Override
    public void onDataChange(DataChangeEvent<CategoryAmount> event) {
        Set<CategoryAmount> categoryAmounts = this.dataProvider.fetch(this.selectedYearMonth).collect(Collectors.toSet());
        this.view.update(categoryAmounts);
    }
}
