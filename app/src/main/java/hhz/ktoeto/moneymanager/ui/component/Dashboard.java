package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Dashboard extends VerticalLayout {

    private final Tab tabMain = new Tab("Главная");
    private final Tab tabStats = new Tab("Статистика");
    private final Tab tabPlanning = new Tab("Планирование");

    private final Div pageMain = new Div();
    private final Div pageStats = new Div();
    private final Div pagePlanning = new Div();

    public Dashboard() {
        setSizeFull();
        addClassName("dashboard-root");

        add(new AppToolbar());

        Tabs tabs = new Tabs(tabMain, tabStats, tabPlanning);
        tabs.addSelectedChangeListener(this::onTabChanged);
        tabs.setFlexGrowForEnclosedTabs(1);

        // Extra control (похож на p-select в tablist)
        Select<String> periodSelect = new Select<>();
        periodSelect.add("All");
        periodSelect.add("Month");
        periodSelect.add("Week");
        periodSelect.setLabel("");
        periodSelect.setPlaceholder("Период");
        HorizontalLayout tabsBar = new HorizontalLayout(tabs, periodSelect);
        tabsBar.setWidthFull();
        tabsBar.expand(tabs);

        // main page content
        pageMain.add(new AddTransaction());
        pageMain.add(new SummaryCard());
        pageMain.add(new TransactionsHistory());

        pageStats.add(new CategoriesCharts());
        pagePlanning.add(new AllCategories());
        pagePlanning.add(new BudgetGoals());

        // initial visibility
        pageMain.setVisible(true);
        pageStats.setVisible(false);
        pagePlanning.setVisible(false);

        add(tabsBar, pageMain, pageStats, pagePlanning);
    }

    private void onTabChanged(Tabs.SelectedChangeEvent ev) {
        Tab sel = ev.getSelectedTab();
        pageMain.setVisible(sel == tabMain);
        pageStats.setVisible(sel == tabStats);
        pagePlanning.setVisible(sel == tabPlanning);
    }
}
