package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.yaxis.labels.builder.StyleBuilder;

public class AreaChartBuilder extends ApexChartsBuilder {

    public AreaChartBuilder() {
        this.withChart(ChartBuilder.get()
                        .withType(Type.AREA)
                        .withHeight("500")
                        .build())
                .withStroke(StrokeBuilder.get()
                        .withCurve(Curve.STRAIGHT)
                        .build())
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.TOP)
                        .withLabels(LabelsBuilder.get()
                                .withUseSeriesColors(true)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withLabels(com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder.get()
                                .withStyle(StyleBuilder.get()
                                        .withColor("#d8dee9")
                                        .build())
                                .build())
                        .build())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withChart(ChartBuilder.get()
                                        .withHeight("300")
                                        .withZoom(ZoomBuilder.get()
                                                .withEnabled(false)
                                                .build())
                                        .build())
                                .build())
                        .build());
    }
}
