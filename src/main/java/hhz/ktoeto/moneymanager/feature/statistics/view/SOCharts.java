package hhz.ktoeto.moneymanager.feature.statistics.view;


import com.storedobject.chart.CategoryData;
import com.storedobject.chart.Data;
import com.storedobject.chart.PieChart;
import com.storedobject.chart.SOChart;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.CategoryAmount;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.math.BigDecimal;
import java.util.Set;

public class SOCharts extends Composite<FlexLayout>implements View, HasUpdatableData<Set<CategoryAmount>> {

    private final ExpensesPiePresenter presenter;

    public SOCharts(ExpensesPiePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.AlignContent.START,
                LumoUtility.AlignItems.STRETCH
        );

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(Set<CategoryAmount> data) {
        FlexLayout root = this.getContent();
        root.removeAll();

        if (data.isEmpty()) {
            EmptyDataImage emptyDataImage = new EmptyDataImage();
            emptyDataImage.setText("Нет транзакций для отображения статистики");
            emptyDataImage.setImageMaxWidth(9, Unit.REM);
            root.add(emptyDataImage);
            return;
        }

        SOChart chart = new SOChart();
        PieChart pieChart = this.mapToChart(data);
        chart.add(pieChart);

        root.add(chart);
    }

    private PieChart mapToChart(Set<CategoryAmount> data) {
        CategoryData categoryNames = new CategoryData(data.stream()
                .map(CategoryAmount::categoryName)
                .toArray(String[]::new));
        Data categoryAmounts = new Data(data.stream()
                .map(CategoryAmount::amount)
                .map(BigDecimal::doubleValue)
                .toArray(Double[]::new));

        return new PieChart(categoryNames, categoryAmounts);
    }
}
