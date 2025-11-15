package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.CategorySum;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.component.chart.CategorySumDonutBuilder;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class CategoryDonutView extends Composite<FlexLayout> implements View, HasUpdatableData<List<CategorySum>> {

    private final CategoryDonutPresenter presenter;
    private final RussianDateRangePicker dateRangePicker;
    private final IncomeExpenseToggle<Transaction.Type> incomeExpenseToggle;

    private Component visibleComponent;

    public CategoryDonutView(CategoryDonutPresenter presenter) {
        this.presenter = presenter;

        this.dateRangePicker = new RussianDateRangePicker("Период");
        this.incomeExpenseToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
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
            DateRange dateRange = event.getValue();
            this.presenter.setDates(dateRange.getStartDate(), dateRange.getEndDate());
        });
        this.dateRangePicker.addAttachListener(event -> {
            DateRange dateRange = new DateRange(
                    LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
                    LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
            );
            this.dateRangePicker.setValue(dateRange);
        });

        root.add(settingsContainer);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(List<CategorySum> data) {
        FlexLayout root = this.getContent();
        if (this.visibleComponent != null) {
            root.remove(this.visibleComponent);
        }
        if (data.isEmpty()) {
            EmptyDataImage image = new EmptyDataImage();
            image.setText("Нет транзакций для отображения статистики");
            image.setImageMaxWidth(13, Unit.REM);

            this.visibleComponent = image;
        } else {
            this.visibleComponent = new CategorySumDonutBuilder(data).build();
        }

        root.add(this.visibleComponent);
    }

    Transaction.Type getSelectedTransactionType() {
        return incomeExpenseToggle.getValue();
    }
}
