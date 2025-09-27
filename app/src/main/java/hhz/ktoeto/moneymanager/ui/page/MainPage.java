package hhz.ktoeto.moneymanager.ui.page;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.card.AddTransactionCard;
import hhz.ktoeto.moneymanager.user.model.User;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@UIScope
@Component
@Route("")
@PermitAll
public class MainPage extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            event.forwardTo(LoginPage.class);
        }
    }

    public MainPage(CategoryService categoryService) {
        List<Category> categories = categoryService.findAll(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        );

        var card = new AddTransactionCard();
        card.addCategories(categories);

        setAlignItems(Alignment.CENTER);
        add(card);
    }
}
