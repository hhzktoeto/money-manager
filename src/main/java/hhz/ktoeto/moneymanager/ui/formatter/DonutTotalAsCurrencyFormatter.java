package hhz.ktoeto.moneymanager.ui.formatter;

import com.github.appreciated.apexcharts.helper.Formatter;

public class DonutTotalAsCurrencyFormatter implements Formatter {

    @Override
    public String getString() {
        return """
                function(context) {
                    const formatter = new Intl.NumberFormat("ru", {
                        style: 'currency',
                        currency: 'RUB'
                    });
                    return formatter.format(context.config.series.reduce((sum, value) => sum + value, 0));
                }
            """;
    }
}
