package hhz.ktoeto.moneymanager.ui.component.chart;

import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;

import java.math.BigDecimal;
import java.util.Collection;

public class CategorySumDonutBuilder extends DonutChartBuilder {

    public CategorySumDonutBuilder(Collection<CategorySum> data) {
        super(data.stream()
                        .map(CategorySum::categoryName)
                        .toList(),
                data.stream()
                        .map(CategorySum::sum)
                        .map(BigDecimal::doubleValue)
                        .toList()
        );
    }
}
