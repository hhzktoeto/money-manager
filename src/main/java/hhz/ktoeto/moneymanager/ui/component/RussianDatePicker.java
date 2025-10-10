package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RussianDatePicker extends DatePicker {

    public RussianDatePicker(String label, LocalDate initialDate) {
        super(label, initialDate);
        DatePicker.DatePickerI18n datePickerI18n = new DatePicker.DatePickerI18n();
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.of("ru", "RU"));

        datePickerI18n.setMonthNames(List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"));
        datePickerI18n.setFirstDayOfWeek(1);
        datePickerI18n.setWeekdays(Arrays.stream(symbols.getWeekdays())
                .filter(s -> !s.isEmpty())
                .toList());
        datePickerI18n.setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays())
                .filter(s -> !s.isEmpty())
                .toList());
        datePickerI18n.setDateFormat("dd.MM.yyyy");
        datePickerI18n.setToday("Сегодня");
        datePickerI18n.setCancel("Закрыть");

        this.setI18n(datePickerI18n);
    }
}
