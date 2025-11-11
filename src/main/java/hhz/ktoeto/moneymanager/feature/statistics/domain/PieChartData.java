package hhz.ktoeto.moneymanager.feature.statistics.domain;

import java.util.List;

public record PieChartData(
        List<String> labels,
        List<Double> data
) {
}
