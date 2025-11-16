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
import hhz.ktoeto.moneymanager.ui.component.chart.CategorySumDonut;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;

import java.util.List;

public class CategoryDonutView extends Composite<FlexLayout> implements View, HasUpdatableData<List<CategorySum>> {

    private final CategoryDonutPresenter presenter;
    private final RussianDateRangePicker dateRangePicker;
    private final IncomeExpenseToggle<Transaction.Type> incomeExpenseToggle;

    private final EmptyDataImage emptyDataImage;
    private final CategorySumDonut categorySumDonut;

    public CategoryDonutView(CategoryDonutPresenter presenter) {
        this.presenter = presenter;

        this.dateRangePicker = new RussianDateRangePicker("Период");
        this.incomeExpenseToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);

        this.emptyDataImage = new EmptyDataImage();
        this.emptyDataImage.setText("Нет транзакций для отображения статистики");
        this.emptyDataImage.setImageMaxWidth(16, Unit.REM);
        this.categorySumDonut = new CategorySumDonut();
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
        this.dateRangePicker.addAttachListener(event ->
                this.dateRangePicker.setValue(new DateRange(this.presenter.getFromDate(), this.presenter.getToDate()))
        );

        FlexLayout donutContainer = new FlexLayout(this.emptyDataImage, this.categorySumDonut);
        donutContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER
        );

        root.add(settingsContainer, donutContainer);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(List<CategorySum> data) {
        if (data.isEmpty()) {
            this.emptyDataImage.setVisible(true);
            this.categorySumDonut.setVisible(false);
        } else {
            this.emptyDataImage.setVisible(false);
            this.categorySumDonut.updateData(data);
            this.categorySumDonut.setVisible(true);
        }
    }

    Transaction.Type getSelectedTransactionType() {
        return incomeExpenseToggle.getValue();
    }
}
