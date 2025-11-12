package hhz.ktoeto.moneymanager.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.subtitle.builder.StyleBuilder;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.Collection;

public class DonutChartBuilder extends ApexChartsBuilder {

    public DonutChartBuilder(String title, Collection<String> labels, Collection<Double> series) {
        this.withTitle(TitleSubtitleBuilder.get()
                        .withText(title)
                        .withAlign(Align.CENTER)
                        .withStyle(StyleBuilder.get()
                                .withColor(StyleConstants.Color.SECONDARY_TEXT)
                                .build())
                        .build())
                .withChart(ChartBuilder.get()
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
                        .withPosition(Position.RIGHT)
                        .withLabels(LabelsBuilder.get()
                                .withUseSeriesColors(true)
                                .build())
                        .build());
    }
}
