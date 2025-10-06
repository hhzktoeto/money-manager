package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionCreateFormLogic;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionForm;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionFormFactory;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionCreateFormLayoutProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@Slf4j
@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionCreateComponent extends Composite<Dialog> {

    private final transient TransactionFormFactory transactionFormFactory;
    private final transient TransactionCreateFormLayoutProvider transactionFormLayoutProvider;

    private final Button closeButton = new Button(VaadinIcon.CLOSE.create());
    private final HorizontalLayout header = new HorizontalLayout(new H3("Добавить транзакцию"), closeButton);

    private TransactionForm transactionForm;

    public void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    public void close() {
        this.getContent().close();
    }

    @Override
    protected Dialog initContent() {
        transactionForm = transactionFormFactory.transactionCreateForm();
        Component formLayout = transactionFormLayoutProvider.createLayout(transactionForm);
        Dialog root = new Dialog(header, formLayout);

        closeButton.addClickListener(e -> this.close());

        root.setCloseOnOutsideClick(false);
        root.addClassName("add-transaction-modal");

        return root;
    }

    @EventListener(TransactionAddedEvent.class)
    private void onTransactionAdded() {
        UI.getCurrent().access(transactionForm::refreshCategories);
    }
}


