package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TransactionSumAreaBuilder extends AreaChartBuilder {

    public TransactionSumAreaBuilder(List<TransactionSum> data) {
        super();
        String[] xLabels = data.stream()
                .map(transactionSum -> {
                    YearMonth yearMonth = YearMonth.of(transactionSum.year(), transactionSum.month());
                    return yearMonth.format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.of("ru")));
                })
                .toArray(String[]::new);
        Double[] expenses = data.stream()
                .map(transactionSum -> transactionSum.expensesSum().doubleValue())
                .toArray(Double[]::new);
        Double[] incomes = data.stream()
                .map(transactionSum -> transactionSum.incomesSum().doubleValue())
                .toArray(Double[]::new);

        this.withXaxis(XAxisBuilder.get()
                        .withCategories(xLabels)
                        .withLabels(LabelsBuilder.get()
                                .withStyle(StyleBuilder.get()
                                        .withColors(List.of("#d8dee9"))
                                        .build())
                                .build())
                        .build())
                .withSeries(new Series<>("Расходы", expenses), new Series<>("Доходы", incomes))
                .withColors("#bf616a", "#a3be8c");
    }
}
