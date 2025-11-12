package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.storedobject.chart.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategoryAmount;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.Statistics;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CategoryPiesView extends Composite<FlexLayout> implements View, HasUpdatableData<Statistics> {

    private final CategoryPiesPresenter presenter;
    private final ToggleButtonGroup<YearMonth> yearMonthPicker;

    public CategoryPiesView(CategoryPiesPresenter presenter) {
        this.presenter = presenter;
        this.yearMonthPicker = new ToggleButtonGroup<>();
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        List<YearMonth> items = presenter.getAvailableYearMonths();
        this.yearMonthPicker.setItems(items);
        this.yearMonthPicker.addValueChangeListener(event ->
                this.presenter.setSelectedYearMonth(event.getValue())
        );
        this.yearMonthPicker.setItemLabelGenerator(yearMonth -> yearMonth.format(DateTimeFormatter.ofPattern("MMM yyyy").withLocale(Locale.of("ru"))));
        this.yearMonthPicker.setToggleable(false);

        root.add(this.yearMonthPicker);
        root.addAttachListener(attachEvent -> this.yearMonthPicker.setValue(items.getLast()));
        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(Statistics data) {
        FlexLayout root = this.getContent();
        root.removeAll();
        root.add(this.yearMonthPicker);

        FlexLayout piesLayout = new FlexLayout();
        piesLayout.addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_COLUMN,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.AlignContent.START,
                LumoUtility.AlignItems.STRETCH
        );

        List<CategoryAmount> expenses = data.expensesCategoryAmounts();
        FlexLayout expensesChartContainer = this.getChartContainer(expenses, "Нет расходов для отображения статистики");
        piesLayout.add(expensesChartContainer);

        List<CategoryAmount> incomes = data.incomesCategoryAmounts();
        FlexLayout incomesChartContainer = this.getChartContainer(incomes, "Нет доходов для отображения статистики");
        piesLayout.add(incomesChartContainer);

        root.add(piesLayout);
    }

    private FlexLayout getChartContainer(Collection<CategoryAmount> data, String emptyDataImageText) {
        FlexLayout container = new FlexLayout();
        container.addClassNames(LumoUtility.Width.FULL);

        if (data.isEmpty()) {
            EmptyDataImage image = new EmptyDataImage();
            image.setText(emptyDataImageText);
            image.setImageMaxWidth(9, Unit.REM);
            container.add(image);
        } else {
            CategoryData categoryNames = new CategoryData(data.stream()
                    .map(CategoryAmount::categoryName)
                    .toArray(String[]::new));
            Data categoryAmounts = new Data(data.stream()
                    .map(CategoryAmount::amount)
                    .map(BigDecimal::doubleValue)
                    .toArray(Double[]::new));

            PieChart pieChart = new PieChart(categoryNames, categoryAmounts);
            pieChart.setColors(StyleConstants.CHARTS_COLORS);

            SOChart chart = new SOChart();
            chart.add(pieChart);
            chart.disableDefaultLegend();
            chart.getDefaultTooltip().setName("АААААААА");

            container.add(chart);
        }

        return container;
    }
}
