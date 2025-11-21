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
import java.util.Map;

public class RussianDateRangePicker extends DateRangePicker<SimpleDateRange> {

    private static final Map<SimpleDateRange, String> DATE_RANGE_MAPPINGS = Map.of(
            SimpleDateRanges.TODAY, "Сегодня",
            SimpleDateRanges.DAY, "День",
            SimpleDateRanges.WEEK, "Неделя",
            SimpleDateRanges.MONTH, "Месяц",
            SimpleDateRanges.QUARTER, "Квартал",
            SimpleDateRanges.HALF_YEAR, "Полугодие",
            SimpleDateRanges.YEAR, "Год",
            SimpleDateRanges.FREE, "Свободный"
    );

    public RussianDateRangePicker() {
        super(() -> new DateRangeModel<>(
                        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
                        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
                        SimpleDateRanges.MONTH),
                SimpleDateRanges.allValues()
        );
        this.withDateRangeLocalizerFunction(dr -> DATE_RANGE_MAPPINGS.getOrDefault(dr, "?"))
                .withStartLabel("Начало")
                .withEndLabel("Конец")
                .withDateRangeOptionsLabel("Диапазон")
                .withDatePickerI18n(this.getI18n())
                .withFormatLocale(Locale.of("RU", "ru"))
                .withAllowRangeLimitExceeding(false);
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
