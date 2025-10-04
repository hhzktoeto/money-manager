package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class AddTransactionModal extends Composite<Dialog> {

    private final TransactionService transactionService;

    public void open() {
        this.getContent().open();
    }

    @Override
    protected Dialog initContent() {
        Dialog dialog = new Dialog();
        dialog.add(new H1("Add Transaction"));
        dialog.setModal(true);
        dialog.setCloseOnOutsideClick(false);
        Button button = new Button("close");
        dialog.add(button);
        button.addClickListener(e -> dialog.close());

        return dialog;
    }
}
