package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.transaction.event.OpenTransactionCreateDialog;
import hhz.ktoeto.moneymanager.ui.transaction.event.TransactionCreationCancelledEvent;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionForm;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionFormFactory;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
public class TransactionCreateDialog extends Composite<Dialog> {

    private final TransactionFormFactory formFactory;

    private Button closeButton;
    private HorizontalLayout header;

    public TransactionCreateDialog(TransactionFormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        root.setCloseOnOutsideClick(false);

        header = new HorizontalLayout();
        header.add(new H3("Добавить транзакцию"));
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addClickListener(e -> this.close());
        header.add(closeButton);

        TransactionForm form = formFactory.transactionCreateForm();
        form.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        root.add(header);
        root.add(form);

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


