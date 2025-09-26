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
    }

    private void onTabChanged(Tabs.SelectedChangeEvent ev) {
        Tab sel = ev.getSelectedTab();
        pageMain.setVisible(sel == tabMain);
        pageStats.setVisible(sel == tabStats);
        pagePlanning.setVisible(sel == tabPlanning);
    }
}
