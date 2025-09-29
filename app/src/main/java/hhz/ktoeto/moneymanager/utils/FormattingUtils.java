package hhz.ktoeto.moneymanager.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public final class FormattingUtils {

    private FormattingUtils() {}


    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendPattern(" yyyy")
            .toFormatter(Locale.of("ru"));

    private static final DecimalFormat AMOUNT_FORMATTER;
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        AMOUNT_FORMATTER = new DecimalFormat("#,##0.00", symbols);
    }

    public static String formatDate(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    public static String formatAmountWithCurrency(BigDecimal amount) {
        return doFormatAmount(amount, true);
    }

    public static String formatAmount(BigDecimal amount) {
        return doFormatAmount(amount, false);
    }

    private static String doFormatAmount(BigDecimal amount, boolean withCurrency) {
        StringBuilder sb = new StringBuilder(AMOUNT_FORMATTER.format(amount));
        if (withCurrency) {
            sb.append("₽");
        }
        return sb.toString();
    }
}
