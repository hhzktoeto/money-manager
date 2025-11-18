package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RussianDatePicker extends DatePicker {

    public RussianDatePicker(String label, LocalDate initialDate) {
        super(label, initialDate);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.of("ru", "RU"));
        DatePicker.DatePickerI18n datePickerI18n = new DatePicker.DatePickerI18n()
                .setMonthNames(List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
                .setFirstDayOfWeek(1)
                .setWeekdays(Arrays.stream(symbols.getWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setDateFormat("dd.MM.yyyy")
                .setToday("Сегодня")
                .setCancel("Закрыть");

        this.setI18n(datePickerI18n);
    }
}
