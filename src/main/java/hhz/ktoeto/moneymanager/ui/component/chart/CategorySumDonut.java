package hhz.ktoeto.moneymanager.ui.component.chart;

import com.storedobject.chart.*;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;

import java.util.Collection;

public class CategorySumDonut extends CustomDonutChart {

    public CategorySumDonut(Collection<CategorySum> data) {
        super(data.stream()
                        .map(categorySum -> "%s\n%s".formatted(categorySum.categoryName(), categorySum.sum()))
                        .toList(),
                data.stream()
                        .map(categorySum -> categorySum.sum().doubleValue())
                        .toList()
        );
        this.setEmphasis(new Emphasis());
    }

    public Legend getLegend() {
        TextStyle textStyle = new TextStyle();
        Font.Family fontFamily = Font.Family.create("Fira Sans Condensed");
        Font.Size fontSize = Font.Size.pixels(16);

        textStyle.setFontStyle(new Font(fontFamily, fontSize));
        // somehow, this makes text match the category color
        textStyle.setColor(new Color("var()"));

        Position legendPosition = new Position();
        legendPosition.setBottom(Size.percentage(0));
        legendPosition.setTop(Size.percentage(80));

        Legend legend = new Legend();
        legend.setTextStyle(textStyle);
        legend.setPosition(legendPosition);

        Position chartPosition = new Position();
        chartPosition.setBottom(Size.percentage(15));
        chartPosition.setTop(Size.percentage(0));
        this.setPosition(chartPosition);

        return legend;
    }

    private static final class Emphasis extends com.storedobject.chart.Chart.Emphasis {

        public Emphasis() {
            super();
        }

        @Override
        public void encodeJSON(StringBuilder sb) {
            super.encodeJSON(sb);
            ComponentPart.addComma(sb);
            sb.append("""
                    "label": {
                        "show": true,
                        "color": "var()",
                        "fontWeight": "bold"
                    }
                    """);
        }
    }
}
