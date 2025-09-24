package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;

public class AppToolbar extends HorizontalLayout {
    public AppToolbar() {
        setWidthFull();
        Span title = new Span("Панель");
        Button settings = new Button("Настройки");
        add(title, settings);
        expand(title);
        setAlignItems(Alignment.CENTER);
    }
}
