package hhz.ktoeto.moneymanager.ui.formatter;

import com.github.appreciated.apexcharts.helper.Formatter;

public class DoubleAsCurrencyFormatter implements Formatter {

    @Override
    public String getString() {
        return """
                function(value) {
                    const formatter = new Intl.NumberFormat("ru", {
                        style: 'currency',
                        currency: 'RUB'
                    });
                    return formatter.format(value);
                }
                """;

    }
}
