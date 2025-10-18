package hhz.ktoeto.moneymanager.ui.feature.category;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import hhz.ktoeto.moneymanager.ui.MainLayout;
import jakarta.annotation.security.PermitAll;

@UIScope
@PermitAll
@SpringComponent
@Route(value = Routes.Path.CATEGORIES, layout = MainLayout.class)
public class CategoryView extends VerticalLayout {

    public CategoryView() {
        setSizeFull();
    }
}
