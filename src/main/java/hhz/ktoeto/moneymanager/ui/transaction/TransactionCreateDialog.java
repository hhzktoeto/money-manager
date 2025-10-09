package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.LayoutProvider;
import hhz.ktoeto.moneymanager.ui.transaction.event.OpenTransactionCreateDialog;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionCreationCancelledEvent;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionForm;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionFormFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class TransactionCreateDialog extends Composite<Dialog> {

    private final Button closeButton;
    private final HorizontalLayout header;

    private final Component transactionFormContainer;

    public TransactionCreateDialog(TransactionFormFactory formFactory,
                                   @Qualifier("transactionCreate") LayoutProvider<TransactionForm> formLayoutProvider) {
        this.closeButton = new Button(VaadinIcon.CLOSE.create());
        this.header = new HorizontalLayout(new H3("Добавить транзакцию"), closeButton);
        this.transactionFormContainer = formLayoutProvider.createLayout(formFactory.transactionCreateForm());
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog(header, transactionFormContainer);

        closeButton.addClickListener(e -> this.close());

        root.setCloseOnOutsideClick(false);
        root.addClassName("transaction-create-dialog");
        header.addClassName("header");
        transactionFormContainer.addClassName("content");

        return root;
    }

    @EventListener(OpenTransactionCreateDialog.class)
    private void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    @EventListener(TransactionCreationCancelledEvent.class)
    private void close() {
        this.getContent().close();
    }

}


