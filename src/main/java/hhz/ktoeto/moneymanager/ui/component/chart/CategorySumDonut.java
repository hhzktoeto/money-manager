package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.ItemMarginBuilder;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.PieBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.pie.builder.DonutBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.pie.builder.TotalBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.pie.builder.ValueBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.formatter.DonutTotalAsCurrencyFormatter;
import hhz.ktoeto.moneymanager.ui.formatter.DoubleAsCurrencyFormatter;

import java.util.Arrays;
import java.util.Collection;

public class CategorySumDonut extends ApexCharts {

    public CategorySumDonut() {
        super.setChart(this.getChart());
        super.setDataLabels(this.getDataLabels());
        super.setColors(StyleConstants.DONUT_CHARTS_COLORS.toArray(String[]::new));
        super.setLegend(this.getLegend());
        super.setTooltip(this.getTooltip());
        super.setResponsive(this.getResponsive());
        super.setStroke(this.getStroke());
        super.setPlotOptions(this.getPlotOptions());
    }

    public void updateData(Collection<CategorySum> data) {
        Double[] series = data.stream()
                .map(categorySum -> categorySum.sum().doubleValue())
                .toArray(Double[]::new);
        Double seriesSum = Arrays.stream(series).reduce(0.0, Double::sum);
        String[] labels = data.stream()
                .map(categorySum -> {
                    Double value = categorySum.sum().doubleValue();
                    Double percentage = value / seriesSum * 100;
                    return "%s (%.1f%%)".formatted(categorySum.categoryName(), percentage);
                })
                .toArray(String[]::new);

        super.setLabels(labels);
        super.setSeries(series);
        super.updateConfig();
    }

    private Chart getChart() {
        return ChartBuilder.get()
                .withType(Type.DONUT)
                .withHeight("500")
                .build();
    }

    private DataLabels getDataLabels() {
        return DataLabelsBuilder.get()
                .withEnabled(false)
                .build();
    }

    private Legend getLegend() {
        return LegendBuilder.get()
                .withFontFamily(StyleConstants.FontFamily.MAIN_FONT)
                .withPosition(Position.BOTTOM)
                .withItemMargin(ItemMarginBuilder.get()
                        .withVertical(5.0)
                        .build())
                .withHorizontalAlign(HorizontalAlign.CENTER)
                .withHeight(85.0)
                .withLabels(LabelsBuilder.get()
                        .withUseSeriesColors(true)
                        .build())
                .withFontSize("16px")
                .build();
    }

    private Tooltip getTooltip() {
        return TooltipBuilder.get()
                .withEnabled(false)
                .build();
    }

    private Responsive getResponsive() {
        return ResponsiveBuilder.get()
                .withBreakpoint(480.0)
                .withOptions(OptionsBuilder.get()
                        .withLegend(LegendBuilder.get()
                                .withFontSize("14px")
                                .build())
                        .build())
                .build();
    }

    private Stroke getStroke() {
        return StrokeBuilder.get()
                .withWidth(1.0)
                .withColors(StyleConstants.Color.BASE)
                .withShow(true)
                .build();
    }

    private PlotOptions getPlotOptions() {
        return PlotOptionsBuilder.get()
                .withPie(PieBuilder.get()
                        .withExpandOnClick(false)
                        .withDonut(DonutBuilder.get()
                                .withLabels(com.github.appreciated.apexcharts.config.plotoptions.pie.builder.LabelsBuilder.get()
                                        .withShow(true)
                                        .withTotal(TotalBuilder.get()
                                                .withShow(true)
                                                .withLabel("Всего")
                                                .withFormatter(new DonutTotalAsCurrencyFormatter())
                                                .withColor(StyleConstants.Color.BODY_TEXT)
                                                .build())
                                        .withValue(ValueBuilder.get()
                                                .withColor(StyleConstants.Color.BODY_TEXT)
                                                .withFormatter(new DoubleAsCurrencyFormatter())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }
}
