package hhz.ktoeto.moneymanager.core.constant;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import hhz.ktoeto.moneymanager.core.ui.MainView;
import hhz.ktoeto.moneymanager.feature.budget.BudgetView;
import hhz.ktoeto.moneymanager.feature.goals.GoalsView;
import hhz.ktoeto.moneymanager.feature.statistics.StatisticsView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsView;
import hhz.ktoeto.moneymanager.feature.user.LoginView;
import lombok.Getter;

@Getter
public enum Routes {

    MAIN(Path.MAIN, Name.MAIN, MainView.class, VaadinIcon.HOME),
    BUDGET(Path.BUDGET, Name.BUDGET, BudgetView.class, VaadinIcon.CALC_BOOK),
    TRANSACTIONS(Path.TRANSACTIONS, Name.TRANSACTIONS, TransactionsView.class, VaadinIcon.CASH),
    GOALS(Path.GOALS, Name.GOALS, GoalsView.class, VaadinIcon.CLIPBOARD_CHECK),
    STATS(Path.STATISTICS, Name.STATISTICS, StatisticsView.class, VaadinIcon.PIE_BAR_CHART),
    LOGIN(Path.LOGIN, null, LoginView.class, null);

    private final String path;
    private final String name;
    private final Class<? extends Component> viewClass;
    private final VaadinIcon icon;

    Routes(String path, String name, Class<? extends Component> viewClass, VaadinIcon icon) {
        this.path = path;
        this.name = name;
        this.viewClass = viewClass;
        this.icon = icon;
    }

    public boolean isMenuItem() {
        return name != null;
    }

    public static final class Path {

        private Path() {}

        public static final String MAIN = "/";
        public static final String LOGIN = "/login";
        public static final String GOALS = "/goals";
        public static final String BUDGET = "/budget";
        public static final String STATISTICS = "/statistics";
        public static final String TRANSACTIONS = "/transactions";
    }

    public static final class Name {

        private Name() {}

        public static final String MAIN = "Главная";
        public static final String GOALS = "Цели";
        public static final String BUDGET = "Бюджет";
        public static final String STATISTICS = "Статистика";
        public static final String TRANSACTIONS = "Транзакции";
    }
}
