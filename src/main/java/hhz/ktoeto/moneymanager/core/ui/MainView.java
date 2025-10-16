package hhz.ktoeto.moneymanager.core.ui;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.core.ui.component.BasicContainer;
import hhz.ktoeto.moneymanager.core.ui.component.YearMonthPicker;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.feature.transaction.ui.TransactionsGrid;
import hhz.ktoeto.moneymanager.feature.transaction.ui.TransactionsSummary;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.MAIN, layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView(TransactionsGrid transactionsGrid, TransactionsSummary transactionsSummary, FormattingService formattingService) {
        setSizeFull();
        addClassNames(
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.START,
                LumoUtility.Height.FULL
        );

        FlexLayout content = new FlexLayout();
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE
        );

        BasicContainer transactionsGridContainer = new BasicContainer();

        HorizontalLayout gridHeaderLayout = new HorizontalLayout();
        gridHeaderLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.END,
                LumoUtility.JustifyContent.END
        );

        YearMonthPicker yearMonthPicker = new YearMonthPicker(formattingService);
        TransactionFilter currentFilter = transactionsGrid.getCurrentFilter();
        yearMonthPicker.setYear(currentFilter.getFromDate().getYear());
        yearMonthPicker.setMonth(currentFilter.getFromDate().getMonth());
        yearMonthPicker.addChangeEventHandler((from, to) -> {
            TransactionFilter filter = transactionsGrid.getCurrentFilter();
            filter.setFromDate(from);
            filter.setToDate(to);
            transactionsGrid.setCurrentFilter(filter);
        });
        gridHeaderLayout.add(yearMonthPicker);

        transactionsGridContainer.setHeader(gridHeaderLayout);
        transactionsGridContainer.setContent(transactionsGrid);
        transactionsGridContainer.getHeader().addClassName(LumoUtility.Margin.Bottom.MEDIUM);

        content.add(
                transactionsSummary,
                transactionsGridContainer
        );

        add(content);
    }
}
