package hhz.ktoeto.moneymanager.feature.transaction;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.MainLayout;
import hhz.ktoeto.moneymanager.core.ui.component.YearMonthPicker;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.ui.TransactionsGrid;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.TRANSACTIONS, layout = MainLayout.class)
public class TransactionsView extends VerticalLayout {

    public TransactionsView(TransactionsGrid allTransactionsGrid, FormattingService formattingService) {
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

        YearMonthPicker yearMonthPicker = new YearMonthPicker(formattingService);
        TransactionFilter currentFilter = allTransactionsGrid.getCurrentFilter();
        yearMonthPicker.setYear(currentFilter.getFromDate().getYear());
        yearMonthPicker.setMonth(currentFilter.getFromDate().getMonth());
        yearMonthPicker.addChangeEventHandler((from, to) -> {
            TransactionFilter filter = allTransactionsGrid.getCurrentFilter();
            filter.setFromDate(from);
            filter.setToDate(to);
            allTransactionsGrid.setCurrentFilter(filter);
        });
        header.add(yearMonthPicker);

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
