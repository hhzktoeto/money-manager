package hhz.ktoeto.moneymanager.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.view.DashboardView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Layout - разметка. Как будут располагаться на странице элементы.
@Slf4j
@UIScope
@Component
public final class MainLayout extends AppLayout {

    MainLayout() {
        H1 title = new H1("Money Manager");

        Button logout = new Button("Выйти", e -> log.info("Чел нажал на выйти!"));

        HorizontalLayout header = new HorizontalLayout(title, logout);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);

        addToNavbar(header);
        addToDrawer(new Span("Дровер"));

        // Роутеры - перенаправляют на разные View. На класс view вешается аннотация Router и в него можно "перенаправить"
        // Короче в дровер добавляем кнопки вместо вкладок. В центр вставляем контент этих "вкладок"
        RouterLink routerLink = new RouterLink("Жмэк", DashboardView.class);
        addToNavbar(routerLink);
    }
}
