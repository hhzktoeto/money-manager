package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.Collection;

public class DonutChartBuilder extends ApexChartsBuilder {

    public DonutChartBuilder(Collection<String> labels, Collection<Double> series) {
        this.withChart(ChartBuilder.get()
                        .withType(Type.DONUT)
                        .build())
                .withLabels(labels.toArray(String[]::new))
                .withSeries(series.toArray(Double[]::new))
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withColors(StyleConstants.DONUT_CHARTS_COLORS)
                .withLegend(LegendBuilder.get()
                        .withFontFamily("Fira Sans Condensed")
                        .withPosition(Position.BOTTOM)
                        .withLabels(LabelsBuilder.get()
                                .withUseSeriesColors(true)
                                .build())
                        .build());
    }
}
