package hhz.ktoeto.moneymanager.ui.card;


import com.vaadin.flow.component.card.Card;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.ui.form.AddTransactionForm;

import java.util.List;

public class AddTransactionCard extends Card {

    private final AddTransactionForm form = new AddTransactionForm();

    public AddTransactionCard() {
        add(this.form);
    }

    public void addCategories(List<Category> categories) {
        this.form.addCategories(categories);
    }
}
