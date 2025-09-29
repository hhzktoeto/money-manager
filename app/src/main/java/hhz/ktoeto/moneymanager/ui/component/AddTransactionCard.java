package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.broadcast.Broadcaster;
import hhz.ktoeto.moneymanager.broadcast.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.transaction.TransactionDTO;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.form.AddTransactionForm;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

import java.util.List;

@UIScope
@SpringComponent
public class AddTransactionCard extends Card {

    private final transient CategoryService categoryService;
    private final transient TransactionService transactionService;
    private final transient Broadcaster broadcaster;

    private final AddTransactionForm addTransactionForm = new AddTransactionForm();

    public AddTransactionCard(CategoryService categoryService,
                              TransactionService transactionService,
                              Broadcaster broadcaster) {
        this.categoryService = categoryService;
        this.transactionService = transactionService;
        this.broadcaster = broadcaster;

        setWidthFull();
        add(new H2("Добавить транзакцию"));
        add(this.addTransactionForm);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        Long userId = SecurityUtils.getCurrentUser().getId();
        List<Category> categories = categoryService.getAll(userId);
        this.addTransactionForm.addCategories(categories);

        this.addTransactionForm.addSubmitListener(() -> {
            TransactionDTO dto = new TransactionDTO(
                    this.addTransactionForm.type(),
                    this.addTransactionForm.category().getName(),
                    this.addTransactionForm.date(),
                    this.addTransactionForm.amount(),
                    this.addTransactionForm.description()
            );
            transactionService.create(dto, userId);

            broadcaster.broadcast(new TransactionAddedEvent());
        });
    }
}
