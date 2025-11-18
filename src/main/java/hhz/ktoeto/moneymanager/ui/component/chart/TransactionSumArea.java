package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.theme.Mode;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.formatter.DoubleAsCompactFormatter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class TransactionSumArea extends ApexCharts {

    public TransactionSumArea() {
        super.setChart(this.getChart());
        super.setLegend(this.getLegend());
        super.setDataLabels(this.getDataLabels());
        super.setTooltip(this.getTooltip());
        super.setResponsive(this.getResponsive());
        super.setColors(StyleConstants.Color.ERROR, StyleConstants.Color.SUCCESS);
        super.setGrid(this.getGrid());
        super.setStroke(this.getStroke());
        super.setTheme(ThemeBuilder.get()
                .withMode(Mode.DARK)
                .build());
    }

    public void updateData(Collection<TransactionSum> data) {
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

        super.setYaxis(new YAxis[]{this.getYAxis()});
        super.setXaxis(this.getXAxis(xLabels));
        super.setSeries(
                new Series<>(Transaction.Type.EXPENSE.toString(), expenses),
                new Series<>(Transaction.Type.INCOME.toString(), incomes)
        );
        super.updateConfig();
    }

    private Chart getChart() {
        return ChartBuilder.get()
                .withType(Type.AREA)
                .withBackground(StyleConstants.Color.BASE)
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
                .build();
    }

    private YAxis getYAxis() {
        return YAxisBuilder.get()
                .withLabels(com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder.get()
                        .withFormatter(new DoubleAsCompactFormatter())
                        .withStyle(com.github.appreciated.apexcharts.config.yaxis.labels.builder.StyleBuilder.get()
                                .withColors(List.of(StyleConstants.Color.BODY_TEXT))
                                .build())
                        .build())
                .build();
    }

    private XAxis getXAxis(String[] labels) {
        List<String> labelColors = new ArrayList<>(labels.length);
        for (int i = 0; i < labels.length; i++) {
            labelColors.add(StyleConstants.Color.BODY_TEXT);
        }
        return XAxisBuilder.get()
                .withCategories(labels)
                .withLabels(LabelsBuilder.get()
                        .withRotate(-45.0)
                        .withRotateAlways(false)
                        .withStyle(StyleBuilder.get()
                                .withFontSize("12px")
                                .withColors(labelColors)
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

    private Stroke getStroke() {
        return StrokeBuilder.get()
                .withCurve(Curve.STRAIGHT)
                .build();
    }
}

