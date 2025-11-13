package hhz.ktoeto.moneymanager.ui.component.chart;

import com.storedobject.chart.LineChart;
import com.storedobject.chart.SOChart;

public class SOAreaChartBuilder {

    private final SOChart soChart = new SOChart();

    public SOAreaChartBuilder() {
        soChart.setWidthFull();

        LineChart lineChart = new LineChart();
    }
}
