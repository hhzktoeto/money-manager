package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        H1 title = new H1();
        Span m1 = new Span("M");
        Span rest1 = new Span("oney ");
        Span m2 = new Span("M");
        Span rest2 = new Span("anager");

        m1.addClassName("logo-blue");
        rest1.addClassName("logo-light");
        m2.addClassName("logo-blue");
        rest2.addClassName("logo-light");
        title.add(m1, rest1, m2, rest2);
        title.addClassName("app-title");

        Button logout = new Button("Выйти", e -> SecurityUtils.logout());
        RouterLink homeLink = new RouterLink("Главная", MainView.class);

        HorizontalLayout header = new HorizontalLayout(homeLink, title, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);

        addToNavbar(header);
    }
}
