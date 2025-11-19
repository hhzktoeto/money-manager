package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.componentfactory.EnhancedDateRangePicker;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RussianDateRangePicker extends EnhancedDateRangePicker {

    public RussianDateRangePicker(String label) {
        super(label);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.of("ru", "RU"));
        DatePickerI18n datePickerI18n = new DatePickerI18n()
                .setMonthNames(List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
                .setFirstDayOfWeek(1)
                .setWeekdays(Arrays.stream(symbols.getWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays())
                        .filter(s -> !s.isEmpty())
                        .toList())
                .setToday("Сегодня")
                .setThisWeek("Эта неделя")
                .setThisMonth("Этот месяц")
                .setThisYear("Этот год")
                .setCancel("Закрыть")
                .setClear("Очистить");

        this.setI18n(datePickerI18n);
        this.setPattern("dd.MM.yyyy");
        this.setClearButtonVisible(true);
    }

    @Override
    public void setRequiredIndicatorVisible(boolean required) {
        super.setRequiredIndicatorVisible(required);
    }
}
