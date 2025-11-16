package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;

public class TransactionSumArea extends ApexCharts {

    public TransactionSumArea(Collection<TransactionSum> data) {
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

        super.setChart(this.getChart());
        super.setSeries(new Series<>("Расходы", expenses), new Series<>("Доходы", incomes));
        super.setLegend(this.getLegend());
        super.setDataLabels(this.getDataLabels());
        super.setYaxis(new YAxis[]{this.getYAxis()});
        super.setXaxis(this.getXAxis(xLabels));
        super.setTooltip(this.getTooltip());
        super.setResponsive(this.getResponsive());
        super.setColors(StyleConstants.Color.ERROR, StyleConstants.Color.SUCCESS);
        super.setGrid(this.getGrid());
    }

    private Chart getChart() {
        return ChartBuilder.get()
                .withType(Type.AREA)
                .withHeight("500")
                .withToolbar(ToolbarBuilder.get()
                        .withShow(false)
                        .build())
                .build();
    }

    private Legend getLegend() {
        return LegendBuilder.get()
                .withShow(false)
                .build();
    }

    private DataLabels getDataLabels() {
        return DataLabelsBuilder.get()
                .withEnabled(false)
                .withStyle(com.github.appreciated.apexcharts.config.datalables.builder.StyleBuilder.get()
                        .withFontSize("10px")
                        .withFontFamily(StyleConstants.FontFamily.MAIN_FONT)
                        .build())
                .build();
    }

    private YAxis getYAxis() {
        return YAxisBuilder.get()
                .withShow(true)
                .withLabels(com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder.get()
                        .withFormatter("""
                                function(value) {
                                    const formatter = new Intl.NumberFormat("ru", {
                                      notation: "compact",
                                      compactDisplay: "short"
                                    });
                                    return formatter.format(value);
                                }
                                """)
                        .build())
                .build();
    }

    private XAxis getXAxis(String[] labels) {
        return XAxisBuilder.get()
                .withCategories(labels)
                .withLabels(LabelsBuilder.get()
                        .withRotate(-45.0)
                        .withOffsetX(10.0)
                        .withRotateAlways(true)
                        .withStyle(StyleBuilder.get()
                                .withFontSize("12px")
                                .build())
                        .build())
                .withTooltip(TooltipBuilder.get()
                        .withEnabled(false)
                        .build())
                .build();
    }

    private Tooltip getTooltip() {
        return TooltipBuilder.get()
                .withEnabled(true)
                .withFillSeriesColor(false)
                .withStyle(com.github.appreciated.apexcharts.config.tooltip.builder.StyleBuilder.get()
                        .build())
                .withTheme("dark")
                .build();
    }

    private Responsive getResponsive() {
        return ResponsiveBuilder.get()
                .withBreakpoint(480.0)
                .withOptions(OptionsBuilder.get()
                        .withChart(ChartBuilder.get()
                                .withHeight("300")
                                .build())
                        .withXAxis(XAxisBuilder.get()
                                .withLabels(LabelsBuilder.get()
                                        .withStyle(StyleBuilder.get()
                                                .withFontSize("10px")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    private Grid getGrid() {
        return GridBuilder.get()
                .withStrokeDashArray(3.0)
                .withBorderColor(StyleConstants.Color.DISABLED_TEXT)
                .build();
    }
}

