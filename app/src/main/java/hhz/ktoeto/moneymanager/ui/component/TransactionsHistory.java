package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TransactionsHistory extends VerticalLayout {
    public TransactionsHistory() {
        Grid<String> grid = new Grid<>();
        grid.addColumn(s -> s).setHeader("Пример транзакций");
        grid.setItems("Bread 25.5", "Rent 750.0");
        add(grid);
    }
}
