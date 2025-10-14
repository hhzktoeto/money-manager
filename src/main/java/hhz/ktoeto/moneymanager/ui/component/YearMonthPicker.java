package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.utils.FormattingUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public class YearMonthPicker extends Composite<HorizontalLayout> {

    private Integer initialYear = LocalDate.now().getYear();
    private Month initialMonth = LocalDate.now().getMonth();

    private Select<Integer> yearPicker;
    private Select<Month> monthPicker;

    private BiConsumer<LocalDate, LocalDate> changeEventHandler;

    @Override
    protected HorizontalLayout initContent() {
        HorizontalLayout root = new HorizontalLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.SMALL,
                LumoUtility.JustifyContent.END
        );

        yearPicker = new Select<>();
        yearPicker.setWidth(5.5f, Unit.REM);
        yearPicker.setEmptySelectionAllowed(false);
        yearPicker.setLabel("Год");
        yearPicker.setItems(IntStream
                .range(2024, 2099)
                .boxed()
                .toList()
        );
        yearPicker.setValue(initialYear);
        root.add(yearPicker);

        monthPicker = new Select<>();
        monthPicker.setWidth(7.5f, Unit.REM);
        monthPicker.setEmptySelectionAllowed(false);
        monthPicker.setLabel("Месяц");
        monthPicker.setItems(Month.values());
        monthPicker.setValue(initialMonth);
        monthPicker.setItemLabelGenerator(FormattingUtils::formatMonth);
        root.add(monthPicker);

        yearPicker.addValueChangeListener(e -> updateDates());
        monthPicker.addValueChangeListener(e -> updateDates());

        return root;
    }

    public void setYear(Integer year) {
        this.initialYear = year;
    }

    public void setMonth(Month month) {
        this.initialMonth = month;
    }

    public void addChangeEventHandler(BiConsumer<LocalDate, LocalDate> changeEventHandler) {
        this.changeEventHandler = changeEventHandler;
    }

    private void updateDates() {
        YearMonth selected = YearMonth.of(yearPicker.getValue(), monthPicker.getValue());
        int lastDay = selected.getMonth().length(selected.isLeapYear());
        LocalDate from = LocalDate.of(selected.getYear(), selected.getMonth(), 1);
        LocalDate to = LocalDate.of(selected.getYear(), selected.getMonth(), lastDay);
        changeEventHandler.accept(from, to);
    }
}
