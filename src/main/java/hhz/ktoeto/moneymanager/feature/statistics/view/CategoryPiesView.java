package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.Statistics;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.ui.component.chart.CategorySumDonutBuilder;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

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
        Scroller yearMonthScroller = new Scroller(this.yearMonthPicker, Scroller.ScrollDirection.HORIZONTAL);
        yearMonthScroller.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.CENTER
        );
        yearMonthScroller.scrollToTop();
        root.add(yearMonthScroller);

        FlexLayout piesLayout = new FlexLayout();
        piesLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        List<CategorySum> expenses = data.expensesCategoryAmounts();
        FlexLayout expensesChartContainer = this.getChartContainer(expenses, "Расходы", "Нет расходов для отображения статистики");
        piesLayout.add(expensesChartContainer);

        List<CategorySum> incomes = data.incomesCategoryAmounts();
        FlexLayout incomesChartContainer = this.getChartContainer(incomes, "Доходы", "Нет доходов для отображения статистики");
        piesLayout.add(incomesChartContainer);

        root.add(piesLayout);
    }

    private FlexLayout getChartContainer(Collection<CategorySum> data, String pieName, String emptyDataImageText) {
        FlexLayout container = new FlexLayout();
        container.addClassNames(LumoUtility.Width.FULL);

        if (data.isEmpty()) {
            EmptyDataImage image = new EmptyDataImage();
            image.setText(emptyDataImageText);
            image.setImageMaxWidth(9, Unit.REM);
            container.add(image);
        } else {
            ApexCharts chart = new CategorySumDonutBuilder(pieName, data).build();
            chart.setWidthFull();

            container.add(chart);
        }

        return container;
    }
}
