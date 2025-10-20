package hhz.ktoeto.moneymanager.ui.feature.transaction;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.TransactionsGrid;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.TRANSACTIONS, layout = MainLayout.class)
public class TransactionsView extends VerticalLayout {

    public TransactionsView(TransactionsGrid allTransactionsGrid) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );

        HorizontalLayout header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.END,
                LumoUtility.JustifyContent.END
        );

        RussianDateRangePicker dateRangePicker = new RussianDateRangePicker("Период");
        TransactionFilter transactionFilter = allTransactionsGrid.getCurrentFilter();
        dateRangePicker.setValue(new DateRange(transactionFilter.getFromDate(), transactionFilter.getToDate()));
        dateRangePicker.addValueChangeListener(event -> {
            TransactionFilter filter = allTransactionsGrid.getCurrentFilter();
            DateRange selectedRange = dateRangePicker.getValue();
            filter.setFromDate(selectedRange.getStartDate());
            filter.setToDate(selectedRange.getEndDate());
            allTransactionsGrid.setCurrentFilter(filter);
            dateRangePicker.suppressKeyboard();
        });
        header.add(dateRangePicker);

        FlexLayout content = new FlexLayout();
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE
        );
        content.add(header, allTransactionsGrid);

        add(content);
    }
}
