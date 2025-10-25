package hhz.ktoeto.moneymanager.ui.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import hhz.ktoeto.moneymanager.ui.event.*;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.TransactionForm;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.TransactionFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionDialog extends Composite<CustomDialog> {

    private final transient TransactionFormFactory formFactory;

    @EventListener(OpenTransactionCreateDialogEvent.class)
    private void openTransactionCreation() {
        this.getContent().open();

        this.getContent().setTitle("Создать транзакцию");
        TransactionForm form = formFactory.transactionCreateForm();
        this.getContent().addBody(form);
    }

    @EventListener(OpenTransactionEditDialogEvent.class)
    private void openTransactionEdit(OpenTransactionEditDialogEvent event) {
        this.getContent().open();

        this.getContent().setTitle("Редактировать транзакцию");
        TransactionForm form = formFactory.transactionEditForm();
        form.edit(event.getTransaction());
        this.getContent().addBody(form);
    }

    @EventListener({
            TransactionCreationCancelledEvent.class,
            TransactionEditCancelledEvent.class,
            TransactionUpdatedEvent.class,
            TransactionDeletedEvent.class
    })
    private void close() {
        this.getContent().close();
    }
}
