package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.ui.form.AddTransactionForm;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@UIScope
@Component
public class AddTransactionCard extends Card {

    private final CategoryService categoryService;
    private final AddTransactionForm addTransactionForm = new AddTransactionForm();

    public AddTransactionCard(CategoryService categoryService) {
        this.categoryService = categoryService;

        setWidthFull();
        add(new H2("Добавить транзакцию"));
        add(this.addTransactionForm);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        Long userId = SecurityUtils.getCurrentUser().getId();
        List<Category> categories = categoryService.findAll(userId);
        addTransactionForm.addCategories(categories);
    }
}
