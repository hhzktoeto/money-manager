package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TransactionSumAreaBuilder extends ApexChartsBuilder {

    public TransactionSumAreaBuilder(List<TransactionSum> data) {
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

        this.withChart(ChartBuilder.get()
                        .withType(Type.BAR)
                        .withHeight("500")
                        .build())
                .withLegend(LegendBuilder.get()
                        .withShow(false)
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(true)
                        .withStyle(com.github.appreciated.apexcharts.config.datalables.builder.StyleBuilder.get()
                                .withFontSize("10px")
                                .withFontFamily(StyleConstants.FontFamily.MAIN_FONT)
                                .build())
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withLabels(com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder.get()
                                .withStyle(com.github.appreciated.apexcharts.config.yaxis.labels.builder.StyleBuilder.get()
                                        .withFontSize("12px")
                                        .build())
                                .build())
                        .build())
                .withXaxis(XAxisBuilder.get()
                        .withCategories(xLabels)
                        .withLabels(LabelsBuilder.get()
                                .withRotate(-45.0)  // Поворот для длинных лейблов
                                .withStyle(StyleBuilder.get()
                                        .withFontSize("12px")
                                        .build())
                                .build())
                        .build())
                .withTooltip(TooltipBuilder.get()
                        .withEnabled(true)
                        .withFillSeriesColor(false)
                        .build())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withChart(ChartBuilder.get()
                                        .withHeight("300")
                                        .build())
                                .withDataLabels(DataLabelsBuilder.get()
                                        .withEnabled(false)  // Скрыть на мобильке
                                        .build())
                                .withXAxis(XAxisBuilder.get()
                                        .withLabels(LabelsBuilder.get()
                                                .withRotate(-90.0)  // Более крутой поворот
                                                .withStyle(StyleBuilder.get()
                                                        .withFontSize("10px")
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .withSeries(new Series<>("Расходы", expenses), new Series<>("Доходы", incomes))
                .withColors("#bf616a", "#a3be8c");
    }
}

