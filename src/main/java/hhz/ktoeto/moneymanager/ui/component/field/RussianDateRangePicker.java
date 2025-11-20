package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RussianDateRangePicker extends DateRangePicker<SimpleDateRange> {

    public RussianDateRangePicker() {
        super(() -> new DateRangeModel<>(
                        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
                        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
                        SimpleDateRanges.MONTH),
                SimpleDateRanges.allValues()
        );
        this.withDateRangeLocalizerFunction(dr -> {
            if (dr.equals(SimpleDateRanges.TODAY)) return "Сегодня";
            if (dr.equals(SimpleDateRanges.DAY)) return "День";
            if (dr.equals(SimpleDateRanges.WEEK)) return "Неделя";
            if (dr.equals(SimpleDateRanges.MONTH)) return "Месяц";
            if (dr.equals(SimpleDateRanges.QUARTER)) return "Квартал";
            if (dr.equals(SimpleDateRanges.HALF_YEAR)) return "Полугодие";
            if (dr.equals(SimpleDateRanges.YEAR)) return "Год";
            if (dr.equals(SimpleDateRanges.FREE)) return "Свободный";
            return "?";
        })
                .withStartLabel("Начало")
                .withEndLabel("Конец")
                .withDateRangeOptionsLabel("Диапазон")
                .withDatePickerI18n(this.getI18n())
                .withFormatLocale(Locale.of("RU", "ru"));
    }

    @Override
    public void setRequiredIndicatorVisible(boolean required) {
        super.setRequiredIndicatorVisible(required);
    }

    private DatePickerI18n getI18n() {
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.of("ru", "RU"));
        return new DatePickerI18n()
                .setMonthNames(List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
                .setFirstDayOfWeek(1)
                .setWeekdays(Arrays.stream(symbols.getWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setToday("Сегодня")
                .setCancel("Закрыть");
    }
}
