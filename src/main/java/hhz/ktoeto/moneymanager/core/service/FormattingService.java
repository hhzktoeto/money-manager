package hhz.ktoeto.moneymanager.core.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@Service
public class FormattingService {

    private final DateTimeFormatter dateFormatter;
    private final DecimalFormat amountFormatter;

    public FormattingService() {
        this.dateFormatter = new DateTimeFormatterBuilder()
                .appendPattern("dd ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendPattern(" yyyy")
                .toFormatter(Locale.of("ru"));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        amountFormatter = new DecimalFormat("#,##0.00", symbols);
    }

    public String formatDate(LocalDate date) {
        return dateFormatter.format(date);
    }

    public String formatAmount(BigDecimal amount) {
        return amountFormatter.format(amount).concat(" ₽");
    }

    public String formatMonth(Month month) {
        return switch (month) {
            case JANUARY -> "Январь";
            case FEBRUARY -> "Февраль";
            case MARCH -> "Март";
            case APRIL -> "Апрель";
            case MAY -> "Май";
            case JUNE -> "Июнь";
            case JULY -> "Июль";
            case AUGUST -> "Август";
            case SEPTEMBER -> "Сентябрь";
            case OCTOBER -> "Октябрь";
            case NOVEMBER -> "Ноябрь";
            case DECEMBER -> "Декабрь";
        };
    }
}
