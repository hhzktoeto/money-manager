package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.feature.statistics.domain.CategoryAmount;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.YearMonthPicker;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Set;

public class ExpensesPieView extends Composite<FlexLayout> implements View, HasUpdatableData<Set<CategoryAmount>> {

    private final ExpensesPiePresenter presenter;
    private final YearMonthPicker yearMonthPicker;

    public ExpensesPieView(ExpensesPiePresenter presenter) {
        this.presenter = presenter;
        this.yearMonthPicker = new YearMonthPicker(new FormattingService());
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

        this.yearMonthPicker.addChangeEventHandler((from, to) ->
                this.presenter.setSelectedYearMonth(YearMonth.of(from.getYear(), to.getMonth()))
        );

        root.add(this.yearMonthPicker);
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

        root.add(this.yearMonthPicker);
        if (data.isEmpty()) {
            EmptyDataImage emptyDataImage = new EmptyDataImage();
            emptyDataImage.setText("Нет транзакций для отображения статистики");
            emptyDataImage.setImageMaxWidth(9, Unit.REM);
            root.add(emptyDataImage);
            return;
        }

        ApexCharts chart = this.mapToChart(data);
        root.add(chart);
    }

    private ApexCharts mapToChart(Set<CategoryAmount> data) {
        return ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.PIE).build())
                .withLabels(data.stream()
                        .map(CategoryAmount::categoryName)
                        .toArray(String[]::new))
                .withSeries(data.stream()
                        .map(CategoryAmount::amount)
                        .map(BigDecimal::doubleValue)
                        .toArray(Double[]::new))
                .build();
    }
}
