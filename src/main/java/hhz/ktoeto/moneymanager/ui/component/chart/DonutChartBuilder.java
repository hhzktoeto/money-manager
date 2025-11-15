package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.EventsBuilder;
import com.github.appreciated.apexcharts.config.datalables.builder.DropShadowBuilder;
import com.github.appreciated.apexcharts.config.datalables.builder.StyleBuilder;
import com.github.appreciated.apexcharts.config.legend.OnItemClick;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.Collection;

public class DonutChartBuilder extends ApexChartsBuilder {

    public DonutChartBuilder(Collection<String> labels, Collection<Double> series) {
        this.withChart(this.getChart())
                .withLabels(labels.toArray(String[]::new))
                .withSeries(series.toArray(Double[]::new))
                .withDataLabels(this.getDataLabels())
                .withColors(StyleConstants.DONUT_CHARTS_COLORS)
                .withLegend(this.getLegend())
                .withTooltip(this.getTooltip())
                .withResponsive(this.getResponsive())
                .withStroke(this.getStroke());
    }

    private Chart getChart() {
        return ChartBuilder.get()
                .withType(Type.DONUT)
                .withHeight("450")
                .build();
    }

    private DataLabels getDataLabels() {
        return DataLabelsBuilder.get()
                .withStyle(StyleBuilder.get()
                        .withFontFamily(StyleConstants.FontFamily.MAIN_FONT)
                        .build())
                .withDropShadow(DropShadowBuilder.get()
                        .withEnable(true)
                        .withOpacity(1.0)
                        .build())
                .withEnabled(true)
                .build();
    }

    private Legend getLegend() {
        return LegendBuilder.get()
                .withFontFamily(StyleConstants.FontFamily.MAIN_FONT)
                .withPosition(Position.BOTTOM)
                .withLabels(LabelsBuilder.get()
                        .withUseSeriesColors(true)
                        .build())
                .withFontSize("16px")
                .withOnItemClick(new OnItemClick(false))
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
                                .withEvents(EventsBuilder.get()
                                        .withDataPointSelection("")
                                        .build())
                                .build())
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
}
