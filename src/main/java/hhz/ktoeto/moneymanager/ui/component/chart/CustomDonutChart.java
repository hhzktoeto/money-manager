package hhz.ktoeto.moneymanager.ui.component.chart;

import com.storedobject.chart.*;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.Arrays;
import java.util.Collection;

public abstract class CustomDonutChart extends DonutChart {

    public CustomDonutChart(Collection<String> labels, Collection<Double> data) {
        super();
        Color[] colors = Arrays.stream(StyleConstants.DONUT_CHARTS_COLORS)
                .map(Color::new)
                .toArray(Color[]::new);

        Chart.Label label = new Chart.Label();
        label.setVisible(false);
        label.getPosition().center();

        Tooltip tooltip = new Tooltip();
        tooltip.setVisible(false);

        ItemStyle itemStyle = new ItemStyle();
        Border border = new Border();
        border.setRadius(10);
        border.setWidth(3);
        border.setColor(new Color("#2E3440"));
        itemStyle.setBorder(border);

        CategoryData itemNames = new CategoryData(labels.toArray(String[]::new));
        Data itemValues = new Data(data.toArray(Double[]::new));

        this.setTooltip(tooltip);
        this.setItemNames(itemNames);
        this.setData(itemValues);
        this.setColors(colors);
        this.setLabel(label);
        this.setHoleRadius(Size.percentage(35));
        this.setItemStyle(itemStyle);
    }
}
