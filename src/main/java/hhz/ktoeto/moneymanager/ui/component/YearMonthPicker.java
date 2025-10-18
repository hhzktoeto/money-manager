package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class YearMonthPicker extends Composite<HorizontalLayout> {

    private final FormattingService formattingService;

    @Setter
    private Integer year = LocalDate.now().getYear();
    @Setter
    private Month month = LocalDate.now().getMonth();

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
                .range(2024, LocalDate.now().getYear() + 10)
                .boxed()
                .toList()
        );
        yearPicker.setValue(year);
        root.add(yearPicker);

        monthPicker = new Select<>();
        monthPicker.setWidth(7.5f, Unit.REM);
        monthPicker.setEmptySelectionAllowed(false);
        monthPicker.setLabel("Месяц");
        monthPicker.setItems(Month.values());
        monthPicker.setValue(month);
        monthPicker.setItemLabelGenerator(formattingService::formatMonth);
        root.add(monthPicker);

        yearPicker.addValueChangeListener(e -> updateDates());
        monthPicker.addValueChangeListener(e -> updateDates());

        return root;
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
