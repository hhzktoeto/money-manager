package hhz.ktoeto.moneymanager.ui.constant;

import com.vaadin.flow.component.Component;
import hhz.ktoeto.moneymanager.feature.budget.BudgetRouteView;
import hhz.ktoeto.moneymanager.feature.category.CategoryRouteView;
import hhz.ktoeto.moneymanager.feature.home.HomeRouteView;
import hhz.ktoeto.moneymanager.feature.login.LoginRouteView;
import hhz.ktoeto.moneymanager.feature.statistics.StatisticsRouteView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsRouteView;
import lombok.Getter;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@Getter
public enum Routes {

    HOME(Path.HOME, Name.HOME, HomeRouteView.class, MaterialIcons.HOME),
    BUDGET(Path.BUDGET, Name.BUDGET, BudgetRouteView.class, MaterialIcons.SAVINGS),
    TRANSACTIONS(Path.TRANSACTIONS, Name.TRANSACTIONS, TransactionsRouteView.class, MaterialIcons.PAYMENTS),
    CATEGORIES(Path.CATEGORIES, Name.CATEGORIES, CategoryRouteView.class, MaterialIcons.CATEGORY),
    STATS(Path.STATISTICS, Name.STATISTICS, StatisticsRouteView.class, MaterialIcons.PIE_CHART),
    LOGIN(Path.LOGIN, null, LoginRouteView.class, null);

    private final String path;
    private final String name;
    private final Class<? extends Component> viewClass;
    private final MaterialIcons icon;

    Routes(String path, String name, Class<? extends Component> viewClass, MaterialIcons icon) {
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

        public static final String HOME = "/";
        public static final String LOGIN = "/login";
        public static final String CATEGORIES = "/categories";
        public static final String BUDGET = "/budget";
        public static final String STATISTICS = "/statistics";
        public static final String TRANSACTIONS = "/transactions";
    }

    public static final class Name {

        private Name() {}

        public static final String HOME = "Главная";
        public static final String CATEGORIES = "Категории";
        public static final String BUDGET = "Бюджет";
        public static final String STATISTICS = "Статистика";
        public static final String TRANSACTIONS = "Транзакции";
    }
}
