package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PeriodPicker extends Composite<FlexLayout> {

    private final DatePicker from = new RussianDatePicker(null, LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
    private final DatePicker to = new RussianDatePicker(null, LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Medium.ROW,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.SMALL
        );

        Span delimiter = new Span("-");
        delimiter.addClassNames(
                LumoUtility.Display.HIDDEN,
                LumoUtility.Display.Breakpoint.Medium.FLEX
        );

        root.add(from, delimiter, to);

        return root;
    }

    public LocalDate fromDate() {
        return from.getValue();
    }

    public LocalDate toDate() {
        return to.getValue();
    }

    public void onFromDateChange(HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        from.addValueChangeListener(listener);
    }

    public void onToDateChange(HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        to.addValueChangeListener(listener);
    }
}
