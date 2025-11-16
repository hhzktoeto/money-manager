package hhz.ktoeto.moneymanager.ui.formatter;

import com.github.appreciated.apexcharts.helper.Formatter;

public class DoubleAsCompactFormatter implements Formatter {

    @Override
    public String getString() {
        return """
                function(value) {
                    const formatter = new Intl.NumberFormat("ru", {
                        notation: "compact",
                        compactDisplay: "short"
                    });
                    return formatter.format(value);
                }
                """;
    }
}
