package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class YearMonthPicker extends Composite<HorizontalLayout> {

    private final FormattingService formattingService;
    private final Set<YearMonth> availableYearMonths;

    @Setter
    private Integer year = LocalDate.now().getYear();
    @Setter
    private Month month = LocalDate.now().getMonth();

    private YearMonth selectedYearMonth = YearMonth.now();
    private Select<Integer> yearPicker;
    private Select<Month> monthPicker;
    private HorizontalLayout scroller;

    private BiConsumer<LocalDate, LocalDate> changeEventHandler;
    private Consumer<YearMonth> changeEventConsumer;

    @Override
    protected HorizontalLayout initContent() {
        HorizontalLayout root = new HorizontalLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.SMALL,
                LumoUtility.JustifyContent.END
        );

        this.scroller = new HorizontalLayout();
        this.scroller.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Overflow.AUTO,
                LumoUtility.FlexWrap.NOWRAP
        );

        yearPicker = new Select<>();
        yearPicker.setWidth(5.5f, Unit.REM);
        yearPicker.setEmptySelectionAllowed(false);
        yearPicker.setLabel("Год");
        yearPicker.setItems(IntStream
                .range(2024, LocalDate.now().getYear() + 10)
                .boxed()
                .toList()
        );
        yearPicker.setValue(year);
        root.add(yearPicker);

        monthPicker = new Select<>();
        monthPicker.setWidth(7.5f, Unit.REM);
        monthPicker.setEmptySelectionAllowed(false);
        monthPicker.setLabel("Месяц");
        monthPicker.setItems(Month.values());
        monthPicker.setValue(month);
        monthPicker.setItemLabelGenerator(formattingService::formatMonth);
        root.add(monthPicker);

        yearPicker.addValueChangeListener(e -> updateDates());
        monthPicker.addValueChangeListener(e -> updateDates());

        return root;
    }

    public void addChangeEventHandler(BiConsumer<LocalDate, LocalDate> changeEventHandler) {
        this.changeEventHandler = changeEventHandler;
    }

    public void addChangeEventHandler(Consumer<YearMonth> changeEventHandler) {
        this.changeEventConsumer = changeEventHandler;
    }

    private void updateDates() {
        YearMonth selected = YearMonth.of(yearPicker.getValue(), monthPicker.getValue());
        int lastDay = selected.getMonth().length(selected.isLeapYear());
        LocalDate from = LocalDate.of(selected.getYear(), selected.getMonth(), 1);
        LocalDate to = LocalDate.of(selected.getYear(), selected.getMonth(), lastDay);
        changeEventHandler.accept(from, to);
    }

    private void refreshButtons() {
        this.scroller.removeAll();
        if (this.availableYearMonths.isEmpty()) {
            Span emptyMessage = new Span("Нет доступных месяцев с транзакциями");
            emptyMessage.addClassNames(LumoUtility.TextColor.DISABLED);
            this.scroller.add(emptyMessage);
            return;
        }

        this.availableYearMonths.forEach(yearMonth -> {
            String label = yearMonth.format(DateTimeFormatter.ofPattern("MM yyyy").withLocale(Locale.of("ru")));
            Button button = new Button(label);

            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            button.addClassNames(LumoUtility.TextColor.DISABLED);
            if (this.selectedYearMonth.equals(yearMonth)) {
                button.removeClassName(LumoUtility.TextColor.DISABLED);
                button.addClassNames(LumoUtility.TextColor.PRIMARY);
            }
            button.addClickListener(e -> this.changeEventConsumer.accept(yearMonth));

            this.scroller.add(button);
        });
    }
}
