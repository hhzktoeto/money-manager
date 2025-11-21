package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.chart.CategorySumDonut;
import hhz.ktoeto.moneymanager.ui.component.field.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.field.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;

import java.util.List;

public class CategoryDonutView extends Composite<FlexLayout> implements View, HasUpdatableData<List<CategorySum>> {

    private final CategoryDonutPresenter presenter;
    private final RussianDateRangePicker dateRangePicker;
    private final IncomeExpenseToggle<Transaction.Type> incomeExpenseToggle;

    private final EmptyDataImage emptyDataImage;
    private final FlexLayout donutContainer;

    private CategorySumDonut categorySumDonut;

    public CategoryDonutView(CategoryDonutPresenter presenter) {
        this.presenter = presenter;

        this.dateRangePicker = new RussianDateRangePicker();
        this.incomeExpenseToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);

        this.emptyDataImage = new EmptyDataImage();
        this.emptyDataImage.setText("Нет транзакций для отображения статистики");
        this.emptyDataImage.setImageMaxWidth(16, Unit.REM);
        this.donutContainer = new FlexLayout(this.emptyDataImage);
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

        FlexLayout settingsContainer = new FlexLayout(this.dateRangePicker, this.incomeExpenseToggle);
        settingsContainer.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.Width.FULL,
                LumoUtility.Gap.SMALL,
                LumoUtility.JustifyContent.EVENLY,
                LumoUtility.AlignItems.BASELINE
        );

        this.incomeExpenseToggle.setWidthFull();
        this.incomeExpenseToggle.setValue(Transaction.Type.EXPENSE);
        this.incomeExpenseToggle.addValueChangeListener(event ->
                this.presenter.onDataChange(null)
        );

        this.dateRangePicker.setWidthFull();
        this.dateRangePicker.addValueChangeListener(event -> {
            DateRangeModel<SimpleDateRange> dateRange = event.getValue();
            this.presenter.setDates(dateRange.getStart(), dateRange.getEnd());
        });
        this.dateRangePicker.addAttachListener(event -> {
            SimpleDateRange dateRange = this.dateRangePicker.getDateRange() == null
                    ? SimpleDateRanges.MONTH
                    : this.dateRangePicker.getDateRange();
            DateRangeModel<SimpleDateRange> value = new DateRangeModel<>(
                    this.presenter.getFromDate(),
                    this.presenter.getToDate(),
                    dateRange);
            this.dateRangePicker.setValue(value);
        });

        this.donutContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER
        );

        root.add(settingsContainer, this.donutContainer);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    // a lot of pain is inside this method and it is finally working. Don't touch it until you REALLY sure you can make it work another way
    public void update(List<CategorySum> data) {
        boolean isChartInContainer = this.donutContainer.getChildren()
                .anyMatch(child -> child.equals(this.categorySumDonut));

        if (data.isEmpty()) {
            if (isChartInContainer) {
                this.donutContainer.remove(this.categorySumDonut);
            }
            this.emptyDataImage.setVisible(true);
            return;
        }

        if (this.categorySumDonut == null) {
            this.categorySumDonut = new CategorySumDonut();
        }
        this.categorySumDonut.updateData(data);
        if (!isChartInContainer) {
            this.donutContainer.add(this.categorySumDonut);
        }
        this.emptyDataImage.setVisible(false);
        this.categorySumDonut.setVisible(true);
    }

    Transaction.Type getSelectedTransactionType() {
        return incomeExpenseToggle.getValue();
    }
}
