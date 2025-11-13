package hhz.ktoeto.moneymanager.ui.component.chart;

import com.storedobject.chart.*;
import com.vaadin.flow.component.Unit;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.Arrays;
import java.util.Collection;

public class SODonutChartBuilder {

    private final SOChart soChart = new SOChart();

    public SODonutChartBuilder(Collection<String> labels, Collection<Double> data) {
        soChart.setWidthFull();
        soChart.setMinHeight(25, Unit.REM);
        soChart.setMaxHeight(60, Unit.REM);
        DonutChart donutChart = this.getDonutChart(
                new CategoryData(labels.toArray(String[]::new)),
                new Data(data.toArray(Double[]::new))
        );

        soChart.add(donutChart, this.getLegend());
        soChart.addClassNames();
    }

    public SOChart build() {
        return soChart;
    }

    private DonutChart getDonutChart(AbstractDataProvider<?> chartLabels, DataProvider chartData) {
        Color[] colors = Arrays.stream(StyleConstants.DONUT_CHARTS_COLORS)
                .map(Color::new)
                .toArray(Color[]::new);

        Chart.Label label = new Chart.Label();
        label.setVisible(false);

        ItemStyle itemStyle = new ItemStyle();
        Border border = new Border();
        border.setRadius(12);
        border.setWidth(4);
        border.setColor(new Color("#2E3440"));
        itemStyle.setBorder(border);

        Position position = new Position();
        position.setTop(Size.percentage(0));
        position.setBottom(Size.percentage(20));

        DonutChart chart = new DonutChart(chartLabels, chartData);
        chart.setColors(colors);
        chart.setLabel(label);
        chart.setHoleRadius(Size.percentage(35));
        chart.setItemStyle(itemStyle);
        chart.setPosition(position);

        return chart;
    }

    private Legend getLegend() {
        TextStyle textStyle = new TextStyle();
        Font.Family fontFamily = Font.Family.create("Fira Sans Condensed");
        Font.Size fontSize = Font.Size.pixels(16);

        textStyle.setFontStyle(new Font(fontFamily, fontSize));
        // somehow, this makes text match the category color
        textStyle.setColor(new Color("var()"));

        Position position = new Position();
        position.setBottom(Size.percentage(0));
        position.setTop(Size.percentage(80));

        Legend legend = new Legend();
        legend.setTextStyle(textStyle);
        legend.setPosition(position);

        return legend;
    }
}
