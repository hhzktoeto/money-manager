package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightActions;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.BudgetView;
import hhz.ktoeto.moneymanager.ui.MainView;
import hhz.ktoeto.moneymanager.feature.statistics.StatisticsView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsView;

import java.util.List;

public final class RouterUtils {

    private static final String MAIN_VIEW_NAME = "Главная";
    private static final String TRANSACTIONS_VIEW_NAME = "Транзакции";
    private static final String STATS_VIEW_NAME = "Статистика";
    private static final String BUDGET_VIEW_NAME = "Бюджет";

    private RouterUtils() {
    }

    public static List<RouterLink> desktopRouterLinks() {
        List<RouterLink> links = List.of(
                createLink(MAIN_VIEW_NAME, MainView.class, VaadinIcon.HOME.create()),
                createLink(TRANSACTIONS_VIEW_NAME, TransactionsView.class, VaadinIcon.CASH.create()),
                createLink(STATS_VIEW_NAME, StatisticsView.class, VaadinIcon.PIE_BAR_CHART.create()),
                createLink(BUDGET_VIEW_NAME, BudgetView.class, VaadinIcon.CALC_BOOK.create())
        );
        links.forEach(link -> link.addClassName(LumoUtility.FontSize.MEDIUM));
        return links;
    }

    public static List<RouterLink> mobileRouterLinks() {
        List<RouterLink> links = List.of(
                createLink(MAIN_VIEW_NAME, MainView.class, VaadinIcon.HOME.create()),
                createLink(TRANSACTIONS_VIEW_NAME, TransactionsView.class, VaadinIcon.CASH.create()),
                createLink(STATS_VIEW_NAME, StatisticsView.class, VaadinIcon.PIE_BAR_CHART.create()),
                createLink(BUDGET_VIEW_NAME, BudgetView.class, VaadinIcon.CALC_BOOK.create())
        );
        links.forEach(link -> link.addClassName(LumoUtility.FontSize.XSMALL));
        return links;
    }

    private static RouterLink createLink(String viewName, Class<? extends Component> viewClass, Icon icon) {
        RouterLink link = new RouterLink(viewClass);
        link.setHighlightCondition(HighlightConditions.sameLocation());
        link.setHighlightAction(HighlightActions.toggleClassName(LumoUtility.TextColor.BODY));

        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.TextAlignment.CENTER
        );

        link.add(icon);
        link.add(viewName);

        return link;
    }

    public static final class RouteName {

        public static final String MAIN = "/";
        public static final String LOGIN = "/login";
        public static final String STATISTICS = "/statistics";
        public static final String BUDGET = "/budget";
        public static final String TRANSACTIONS = "/transactions";

        private RouteName() {
        }
    }
}
