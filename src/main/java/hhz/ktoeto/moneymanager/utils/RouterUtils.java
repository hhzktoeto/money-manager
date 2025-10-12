package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightActions;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.view.MainView;
import hhz.ktoeto.moneymanager.ui.view.PlanningView;
import hhz.ktoeto.moneymanager.ui.view.StatsView;
import hhz.ktoeto.moneymanager.ui.view.TransactionsView;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public final class RouterUtils {

    private RouterUtils() {
    }

    public static List<RouterLink> desktopRouterLinks() {
        return List.of(
                createLink("Главная", MainView.class, null),
                createLink("Транзакции", TransactionsView.class, null),
                createLink("Статистика", StatsView.class, null),
                createLink("Планирование", PlanningView.class, null)
        );
    }

    public static List<RouterLink> mobileRouterLinks() {
        return List.of(
                createLink(null, MainView.class, VaadinIcon.HOME.create()),
                createLink(null, TransactionsView.class, VaadinIcon.MONEY.create()),
                createLink(null, StatsView.class, VaadinIcon.PIE_BAR_CHART.create()),
                createLink(null, PlanningView.class, VaadinIcon.CALC_BOOK.create())
        );
    }

    private static RouterLink createLink(@Nullable String viewName, @NonNull Class<? extends Component> viewClass, @Nullable Icon icon) {
        RouterLink link = new RouterLink(viewClass);
        link.setHighlightCondition(HighlightConditions.sameLocation());
        link.setHighlightAction(HighlightActions.toggleClassName(LumoUtility.TextColor.PRIMARY_CONTRAST));
        if (icon != null) {
            link.add(icon);
        }
        if (viewName != null) {
            link.add(viewName);
        }

        return link;
    }

    public static final class RouteName {

        public static final String MAIN = "/";
        public static final String LOGIN = "/login";
        public static final String STATS = "/stats";
        public static final String PLANNING = "/planning";
        public static final String TRANSACTIONS = "/transactions";

        private RouteName() {
        }
    }
}
